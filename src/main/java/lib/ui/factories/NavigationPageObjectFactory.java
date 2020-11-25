package lib.ui.factories;

import lib.Platform;
import lib.ui.NavigationPageObject;
import lib.ui.android.AndroidNavigationPageObject;
import lib.ui.ios.iOSNavigationPageObject;
import lib.ui.mobile_web.MWNavigationPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class NavigationPageObjectFactory {

    public static NavigationPageObject get(RemoteWebDriver driver) {
        if (Platform.getInstance().isAndroid()) {
            return new AndroidNavigationPageObject(driver);
        } else if (Platform.getInstance().isIOS()) {
            return new iOSNavigationPageObject(driver);
        } else {
            return new MWNavigationPageObject(driver);
        }
    }
}
