package Utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Utilities {

    public static WebDriver driver;
    public static WebDriverWait wait;
    public static DevTools devTools;


    public String removeCurrencySymbol(String value) {
        return value.replaceAll("[^\\d\\.\\,\\s]+", "");
    }

}
