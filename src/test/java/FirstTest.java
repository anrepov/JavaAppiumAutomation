import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

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

    @Test
    public void assertResultsByTextNotEmptyThenClear() {
        searchText("Java");
        Assert.assertTrue("Search results is empty", getVisibleSearchResults().size() > 1);

        waitForElementAndClick(By.id("org.wikipedia:id/search_close_btn"), "cant find X button", 5);
        waitForElementNotPresent(By.id("org.wikipedia:id/search_close_btn"), "X button still present", 5);
    }

    private void assertElementHasText(By by, String expectedMessage, String errorMessage) {
        WebElement element = driver.findElement(by);
        Assert.assertEquals(errorMessage, expectedMessage, element.getText());
    }

    private void searchText(String text) {
        waitForElementAndClick(By.className("android.widget.TextView"), "cant find search field", 5);
        waitForElementAndSendKeys(By.id("org.wikipedia:id/search_src_text"), text, "cant find search line", 5);
    }

    private List<WebElement> getVisibleSearchResults() {
        By by = By.xpath("//*[@resource-id = 'org.wikipedia:id/search_results_list']/*[@class='android.view.ViewGroup']");
        waitForElementPresent(by, "cant find search results");
        List<WebElement> elements = driver.findElements(by);
        return elements;
    }

    private WebElement waitForElementPresent(By by, String errorMessage, long timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.withMessage(errorMessage + "\n");

        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private WebElement waitForElementPresent(By by, String errorMessage) {
        return waitForElementPresent(by, errorMessage, 5);
    }

    private WebElement waitForElementAndClick(By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }
}