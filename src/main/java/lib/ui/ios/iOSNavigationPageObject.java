package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.NavigationPageObject;

public class iOSNavigationPageObject extends NavigationPageObject {

    static {
        EXPLORE_BUTTON = "id:Explore";
        MY_LISTS_BUTTON = "id:Saved";
        BACK_BUTTON = "id:Back";
    }

    public iOSNavigationPageObject(AppiumDriver driver) {
        super(driver);
    }

    @Override
    public void goToMainPage() {
        waitForElementAndClick(BACK_BUTTON, "cant find 'Back' button", 5);
    }
}
