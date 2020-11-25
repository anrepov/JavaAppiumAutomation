package lib.ui.mobile_web;

import lib.ui.NavigationPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWNavigationPageObject extends NavigationPageObject {

    static {
        MY_LISTS_BUTTON = "xpath://*[text()='Watchlist']/..";
        MAIN_MENU = "xpath://label[@title= 'Open main menu']";
    }

    public MWNavigationPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}