package lib;

import io.appium.java_client.AppiumDriver;
import junit.framework.TestCase;
import lib.ui.WelcomePageObject;

public class CoreTestCase extends TestCase {

    public AppiumDriver driver;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        driver = Platform.getInstance().getDriver();
        if (Platform.getInstance().isIOS()) skipWelcomePageForIOSApp();
        else if (Platform.getInstance().isAndroid()) driver.navigate().back();
    }

    @Override
    protected void tearDown() throws Exception {
        driver.quit();
        super.tearDown();
    }

    private void skipWelcomePageForIOSApp() {
        WelcomePageObject welcomePageObject = new WelcomePageObject(driver);
        welcomePageObject.clickSkip();
    }
}