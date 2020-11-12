package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.NavigationPageObject;

public class AndroidNavigationPageObject extends NavigationPageObject {

    static {
        MORE_OPTIONS_BUTTON = "xpath://android.widget.ImageView[@content-desc='More options']";
        EXPLORE_BUTTON = "id:org.wikipedia:id/overflow_feed";
        MY_LISTS_BUTTON = "xpath://android.widget.FrameLayout[@content-desc='My lists']";
    }

    public AndroidNavigationPageObject(AppiumDriver driver) {
        super(driver);
    }
}