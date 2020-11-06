package lib.ui;

import io.appium.java_client.AppiumDriver;

public class NavigationPageObject extends MainPageObject {

    private static final String
            MORE_OPTIONS_BUTTON = "xpath://android.widget.ImageView[@content-desc='More options']",
            EXPLORE_BUTTON = "id:org.wikipedia:id/overflow_feed",
            MY_LISTS_BUTTON = "xpath://android.widget.FrameLayout[@content-desc='My lists']";

    public NavigationPageObject(AppiumDriver driver) {
        super(driver);
    }

    public void goToMainPage() {
        waitForElementAndClick(MORE_OPTIONS_BUTTON, "cant find 'More options' button", 5);
        waitForElementAndClick(EXPLORE_BUTTON, "cant find 'Explore' button", 5);
        driver.navigate().back();
    }

    void goToMyListsFromMainPage() {
        waitForElementAndClick(MY_LISTS_BUTTON, "cant find 'My lists' button", 5);
    }
}