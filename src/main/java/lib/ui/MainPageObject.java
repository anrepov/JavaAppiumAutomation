package lib.ui;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.regex.Pattern;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

public class MainPageObject {

    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver) {
        this.driver = driver;
    }

    public static void waitTimeOut(int sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (Exception ex) {
        }
    }

    public void assertElementHasText(String locator, String expectedMessage, String errorMessage) {
        By by = this.getLocatorByString(locator);
        WebElement element = driver.findElement(by);
        assertEquals(errorMessage, expectedMessage, element.getText());
    }

    WebElement waitForElementPresent(String locator, String errorMessage, long timeOutInSeconds) {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.withMessage(errorMessage + "\n");

        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    WebElement waitForElementPresent(String locator, String errorMessage) {
        return waitForElementPresent(locator, errorMessage, 5);
    }

    public WebElement waitForElementAndClick(String locator, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeys(String locator, String value, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        element.clear();
        element.sendKeys(value);
        return element;
    }

    public boolean waitForElementNotPresent(String locator, String errorMessage, long timeoutInSeconds) {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    protected void swipeElementToLeft(String locator, String errorMessage) {
        WebElement element = waitForElementPresent(locator, errorMessage, 10);

        int leftX = element.getLocation().getX();
        int rightX = leftX + element.getSize().getWidth();
        int upperY = element.getLocation().getY();
        int lowerY = upperY + element.getSize().getHeight();
        int middleY = (upperY + lowerY) / 2;

        TouchAction action = new TouchAction(driver);
        action.press(PointOption.point(rightX, middleY)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(300)))
                .moveTo(PointOption.point(leftX, middleY))
                .release().perform();
    }

    String waitForElementAndGetAttribute(String locator, String attribute, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        return element.getAttribute(attribute);
    }

    boolean isElementPresent(String locator, long waitTimeOut) {
        By by = this.getLocatorByString(locator);
        try {
            WebDriverWait wait = new WebDriverWait(driver, waitTimeOut);
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            return true;
        } catch (ElementNotFoundException | TimeoutException ex) {
            return false;
        }
    }

    public void assertElementPresent(String locator) {
        By by = this.getLocatorByString(locator);
        try {
            driver.findElement(by).isDisplayed();
        } catch (Exception ex) {
            fail("element is not displayed");
        }
    }

    protected void swipeUpToFindElement(String locator, String errorMessage, int maxSwipes) {
        By by = this.getLocatorByString(locator);
        int alreadySwiped = 0;

        while (driver.findElements(by).size() == 0) {

            if (alreadySwiped > maxSwipes) {
                waitForElementPresent(locator, "cant find element by swiping up. \n" + errorMessage, 0);
                return;
            }
            swipeUpQuick();
            alreadySwiped++;
        }
    }

    protected void swipeUp(int timeOfSwipe) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int startY = (int) (size.height * 0.8);
        int endY = (int) (size.height * 0.2);

        action.press(PointOption.point(x, startY)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(timeOfSwipe))).moveTo(PointOption.point(x, endY)).release().perform();
    }

    protected void swipeUpQuick() {
        swipeUp(200);
    }

    public By getLocatorByString(String locator_with_type) {
        String[] exploded_locator = locator_with_type.split(Pattern.quote(":"), 2);
        String by_type = exploded_locator[0];
        String locator = exploded_locator[1];

        if (by_type.equals("xpath"))
            return By.xpath(locator);
        else if (by_type.equals("id"))
            return By.id(locator);
        else if (by_type.equals("className"))
            return By.className(locator);
        else throw new IllegalArgumentException("Cannot get type of locator. Locator " + locator_with_type);
    }

    public void clickElementToTheRightUpperCorner(String locator, String errorMessage) {
        WebElement element = this.waitForElementPresent(locator + "/..", errorMessage);
        int rightX = element.getLocation().getX();
        int upperY = element.getLocation().getY();
        int lowerY = upperY + element.getSize().getHeight();
        int middleY = (upperY + lowerY) / 2;
        int width = element.getSize().getWidth();

        int pointToClickX = (rightX + width) - 3;
        int pointToClickY = middleY;

        TouchAction action = new TouchAction(driver);
        action.tap(PointOption.point(pointToClickX, pointToClickY)).perform();
    }
}