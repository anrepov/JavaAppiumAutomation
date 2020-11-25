package lib;

import io.appium.java_client.AppiumDriver;
import junit.framework.TestCase;
import lib.ui.WelcomePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class CoreTestCase extends TestCase {

    public RemoteWebDriver driver;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        driver = Platform.getInstance().getDriver();
        if (Platform.getInstance().isIOS()) skipWelcomePageForIOSApp();
        else if (Platform.getInstance().isAndroid()) driver.navigate().back();
        openWikiWebPageForMobileWeb();
    }

    @Override
    protected void tearDown() throws Exception {
        driver.quit();
        super.tearDown();
    }

    protected void openWikiWebPageForMobileWeb() {
        if (Platform.getInstance().isMw()) {
            driver.get("https://en.m.wikipedia.org");
        } else {
            System.out.println("Method openWikiWebPageForMobileWeb() do nothing for platform " + Platform.getInstance().getPlatformVar());
        }

    }

    private void skipWelcomePageForIOSApp() {
        if (Platform.getInstance().isIOS()) {
            AppiumDriver driver = (AppiumDriver) this.driver;
            WelcomePageObject welcomePageObject = new WelcomePageObject(driver);
            welcomePageObject.clickSkip();
        }
    }
}