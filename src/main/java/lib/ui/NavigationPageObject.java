package lib.ui;

import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class NavigationPageObject extends MainPageObject {

    protected static String
            MORE_OPTIONS_BUTTON,
            EXPLORE_BUTTON,
            MY_LISTS_BUTTON,
            MAIN_MENU,
            BACK_BUTTON;

    public NavigationPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void goToMainPage() {
        waitForElementAndClick(MORE_OPTIONS_BUTTON, "cant find 'More options' button", 5);
        waitForElementAndClick(EXPLORE_BUTTON, "cant find 'Explore' button", 5);
        driver.navigate().back();
    }

    public void goToMyListsFromMainPage() {
        waitForElementAndClick(MY_LISTS_BUTTON, "cant find 'My lists' button", 5);
    }

    public void openMainMenu() {
        waitTimeOut(1);
        waitForElementAndClick(MAIN_MENU, "cant find and click main menu button", 5);
    }
}