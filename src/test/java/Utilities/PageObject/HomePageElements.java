package Utilities.PageObject;

import Utilities.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;


public class HomePageElements extends Utilities {


    @FindBy(how = How.CSS, using = ".btn.btn-sm.btn-link.header-login-button.header-menu-button")
    @CacheLookup
    public WebElement LoginButton;

    @FindBy(how = How.ID, using = "myCasinoModal-label")
    @CacheLookup
    public WebElement SelectYourFavoritesPopupLabel;

    @FindBy(how = How.XPATH, using = "//*[@id=\"myCasinoModal\"]/div[1]/div/div[1]/button")
    @CacheLookup
    public WebElement SelectYourFavoritesPopupCloseButton;

    public HomePageElements(WebDriver driver) {
        Utilities.driver = driver;
    }
}
