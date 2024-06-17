package Utilities.PageObject;

import Utilities.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class AccountLoginElements extends Utilities {


    @FindBy(how = How.ID, using = "login_form[username]")
    @CacheLookup
    public WebElement UserNameInput;

    @FindBy(how = How.ID, using = "login-modal-password-input")
    @CacheLookup
    public WebElement PasswordInput;

    @FindBy(how = How.XPATH, using = "//*[@id=\"login-modal-form\"]/div[5]/button")
    @CacheLookup
    public WebElement LoginSubmitButton;

    public AccountLoginElements(WebDriver driver) {
        Utilities.driver = driver;
    }
}
