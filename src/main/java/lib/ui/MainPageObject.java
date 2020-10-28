package lib.ui;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

public class MainPageObject {

    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver) {
        this.driver = driver;
    }

    public void assertElementHasText(By by, String expectedMessage, String errorMessage) {
        WebElement element = driver.findElement(by);
        assertEquals(errorMessage, expectedMessage, element.getText());
    }

    WebElement waitForElementPresent(By by, String errorMessage, long timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.withMessage(errorMessage + "\n");

        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    WebElement waitForElementPresent(By by, String errorMessage) {
        return waitForElementPresent(by, errorMessage, 5);
    }

    public WebElement waitForElementAndClick(By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeys(By by, String value, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    public boolean waitForElementNotPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    protected void swipeElementToLeft(By by, String errorMessage) {
        WebElement element = waitForElementPresent(by, errorMessage, 10);

        int leftX = element.getLocation().getX();
        int rightX = leftX + element.getSize().getWidth();
        int upperY = element.getLocation().getY();
        int lowerY = upperY + element.getSize().getHeight();
        int middleY = (upperY + lowerY) / 2;

        TouchAction action = new TouchAction(driver);
        action.press(rightX, middleY).waitAction(300).moveTo(leftX, middleY).release().perform();
    }

    String waitForElementAndGetAttribute(By by, String attribute, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        return element.getAttribute(attribute);
    }

    boolean isElementPresent(By by, long waitTimeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, waitTimeOut);
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            return true;
        } catch (ElementNotFoundException | TimeoutException ex) {
            return false;
        }
    }

    public void assertElementPresent(By by) {
        try {
            driver.findElement(by).isDisplayed();
        } catch (Exception ex) {
            fail("element is not displayed");
        }
    }

    void waitTimeOut(int sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (Exception ex) {
        }
    }

    protected void swipeUpToFindElement(By by, String errorMessage, int maxSwipes) {

        int alreadySwiped = 0;

        while (driver.findElements(by).size() == 0) {

            if (alreadySwiped > maxSwipes) {
                waitForElementPresent(by, "cant find element by swiping up. \n" + errorMessage, 0);
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

        action.press(x, startY).waitAction(timeOfSwipe).moveTo(x, endY).release().perform();
    }

    protected void swipeUpQuick() {
        swipeUp(200);
    }

    private void selectFirstResult() {
        waitForElementAndClick(By.xpath(
                "//*[@resource-id = 'org.wikipedia:id/search_results_list']" +
                        "/*[@class='android.view.ViewGroup'][@index = '0']" +
                        "/*[@resource-id = 'org.wikipedia:id/page_list_item_title']"),
                "cant find search field", 5);
    }
}