package lib.ui;

import io.appium.java_client.AppiumDriver;

abstract public class NavigationPageObject extends MainPageObject {

    protected static String
            MORE_OPTIONS_BUTTON,
            EXPLORE_BUTTON,
            MY_LISTS_BUTTON,
            BACK_BUTTON;

    public NavigationPageObject(AppiumDriver driver) {
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
}