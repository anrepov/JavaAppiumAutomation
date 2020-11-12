package lib.ui.factories;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.ui.NavigationPageObject;
import lib.ui.android.AndroidNavigationPageObject;
import lib.ui.ios.iOSNavigationPageObject;

public class NavigationPageObjectFactory {

    public static NavigationPageObject get(AppiumDriver driver) {
        if (Platform.getInstance().isAndroid()) {
            return new AndroidNavigationPageObject(driver);
        } else {
            return new iOSNavigationPageObject(driver);
        }
    }
}
