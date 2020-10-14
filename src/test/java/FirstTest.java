import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "C:\\Users\\anrep\\IdeaProjects\\JavaAppiumAutomation\\apks\\org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

        //Skip selecting language at start
        driver.navigate().back();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void checkSearchFieldText() {
        assertElementHasText(By.className("android.widget.TextView"), "Search Wikipedia", "Строка поиска не содержит ожидаемый текст");
    }

    private void assertElementHasText(By by, String expectedMessage, String errorMessage) {
        WebElement element = driver.findElement(by);
        Assert.assertEquals(errorMessage, expectedMessage, element.getText());
    }
}