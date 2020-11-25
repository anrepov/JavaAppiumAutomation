package lib.ui.android;

import lib.ui.NavigationPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidNavigationPageObject extends NavigationPageObject {

    static {
        MORE_OPTIONS_BUTTON = "xpath://android.widget.ImageView[@content-desc='More options']";
        EXPLORE_BUTTON = "id:org.wikipedia:id/overflow_feed";
        MY_LISTS_BUTTON = "xpath://android.widget.FrameLayout[@content-desc='My lists']";
    }

    public AndroidNavigationPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}