package Tests;

import Utilities.PageObject.AccountLoginElements;
import Utilities.PageObject.HomePageElements;
import Utilities.Utilities;
import Utilities.SettersAndGetters;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.v125.network.Network;
import org.openqa.selenium.devtools.v125.network.model.RequestId;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class AssertBalanceTest extends Utilities {

    protected static HomePageElements homePageElements;
    protected static AccountLoginElements accountLoginElements;


    @Given("Navigate to {string}")
    public void openSite(String siteUrl) {
        homePageElements = PageFactory.initElements(driver, HomePageElements.class);
        devTools = ((ChromeDriver) driver).getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        devTools.addListener(Network.responseReceived(), responseReceived -> {
            String responseUrl = responseReceived.getResponse().getUrl();
            RequestId requestId = responseReceived.getRequestId();
            if (responseUrl.contains("getMemberBalance")) {
                SettersAndGetters settersAndGetters = new SettersAndGetters();
                settersAndGetters.settMemberBalance(devTools.send(Network.getResponseBody(requestId)).getBody());
            }
        });
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(siteUrl);

        wait.until(ExpectedConditions.visibilityOf(homePageElements.LoginButton));

    }

    @And("Click in site with username {string} and password {string}")
    public void clickInSiteWithUsernameAndPassword(String username, String password)  {
        homePageElements.LoginButton.click();
        accountLoginElements = PageFactory.initElements(driver, AccountLoginElements.class);
        wait.until(ExpectedConditions.visibilityOf(accountLoginElements.UserNameInput));
        accountLoginElements.UserNameInput.sendKeys(username);
        accountLoginElements.PasswordInput.sendKeys(password);
        accountLoginElements.LoginSubmitButton.click();
    }

    @When("Close the modals \\(pop-ups) if any upon login")
    public void closeTheModalsPopUpsIfAnyUponLogin() {
        wait.until(ExpectedConditions.visibilityOf(homePageElements.SelectYourFavoritesPopupLabel));
        wait.until(ExpectedConditions.elementToBeClickable(homePageElements.SelectYourFavoritesPopupCloseButton));
        try {
            homePageElements.SelectYourFavoritesPopupCloseButton.isDisplayed();
            homePageElements.SelectYourFavoritesPopupCloseButton.click();
        } catch (NoSuchElementException ignore) {
        }
    }

    @Then("Verify if the balance in the header is the same as the one from the response to request getMemberBalance")
    public void verifyIfTheBalanceInTheHeaderIsTheSameAsTheOneFromTheResponseToRequest() throws InterruptedException {
        Thread.sleep(5000);
        SettersAndGetters settersAndGetters = new SettersAndGetters();
        JSONObject json = new JSONObject(settersAndGetters.getMemberBalance());

        Iterator x = json.getJSONObject("data").keys();
        JSONArray dataArray = new JSONArray();
        while (x.hasNext()) {
            String key = (String) x.next();
            dataArray.put(json.getJSONObject("data").get(key));
        }
        for (int i = 0; i < dataArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) dataArray.get(i);
            if (jsonObject.optJSONObject("info").get("creditType").equals("Balance")) {
                Assert.assertEquals("Wrong balance", jsonObject.optJSONObject("info").get("amount").toString(), driver.findElement(By.className("user-balance-item-amount")).getText());
                // IF test need assert only balance value use this:
                // removeCurrencySymbol -> remove currency from both value ($,€ etc)
//                Assert.assertEquals("Wrong balance", removeCurrencySymbol(jsonObject.optJSONObject("info").get("amount").toString()), removeCurrencySymbol(driver.findElement(By.className("user-balance-item-amount")).getText()));
                break;
            }
        }
    }
}
