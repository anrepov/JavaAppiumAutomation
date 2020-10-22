import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        capabilities.setCapability("orientation", "PORTRAIT");

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

    @Test
    public void assertResultsByTextHasThisText() {
        String textForSearch = "Java";
        searchText(textForSearch);

        List<WebElement> searchResults = getVisibleSearchResults();
        for (int i = 0; i < searchResults.size(); i++) {
            WebElement result = driver.findElement(By.xpath(
                    "//*[@resource-id = 'org.wikipedia:id/search_results_list']" +
                            "/*[@class='android.view.ViewGroup'][@index = '" + i + "']" +
                            "/*[@resource-id = 'org.wikipedia:id/page_list_item_title']"
            ));
            assertTrue(String.format("Текст в заголовке %s результата, равный %s, не совпадает с ожидаемым %s", i + 1, result.getText(), textForSearch),
                    result.getText().contains(textForSearch));
        }
    }

    @Test
    public void saveTwoArticlesToListThenDeleteOne() {
        String articleTitle;
        String listName = "testList";
        searchText("Java");

        articleTitle = "Java (programming language)";
        selectResultWithText(articleTitle);
        addToList(articleTitle, listName);

        driver.navigate().back();

        articleTitle = "Java";
        selectResultWithText(articleTitle);
        addToList(articleTitle, listName);

        goToMainPage();
        openSavedList(listName);
        List<String> articlesBeforeDelete = getCurrentArticlesNames();

        int deletedArticleIndex = deleteAnyArticle();
        String deletedArticleName = articlesBeforeDelete.get(deletedArticleIndex - 1);
        List<String> articlesAfterDelete = getCurrentArticlesNames();

        articlesBeforeDelete.removeAll(articlesAfterDelete);
        assertEquals("fwef", articlesBeforeDelete.get(0), deletedArticleName);
    }

    @Test
    public void assertArticleHasTitle() {
        String articleTitle = "Java (programming language)";
        searchText("Java");
        selectResultWithText(articleTitle);

        assertElementPresent(By.xpath("//android.view.View[@content-desc='" + articleTitle + "']"));
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
        return driver.findElements(by);
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

    private void selectResultWithText(String text) {
        waitForElementAndClick(By.xpath(
                "//*[@resource-id = 'org.wikipedia:id/search_results_list']" +
                        "/*[@class='android.view.ViewGroup']" +
                        "/*[@resource-id = 'org.wikipedia:id/page_list_item_title']" +
                        "[@text = '" + text + "']"),
                "cant find result with text " + text, 5);
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

    private String waitForElementAndGetAttribute(By by, String attribute, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        return element.getAttribute(attribute);
    }

    private boolean isElementPresent(By by, long waitTimeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, waitTimeOut);
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            return true;
        } catch (ElementNotFoundException | TimeoutException ex) {
            return false;
        }
    }

    private void assertElementPresent(By by) {
        try {
            driver.findElement(by).isDisplayed();
        } catch (Exception ex) {
            Assert.fail("element is not displayed");
        }
    }

    private void addToList(String articleName, String listName) {
        waitTimeOut(2);
        waitForElementAndClick(By.xpath("//*[@resource-id = 'org.wikipedia:id/page_actions_tab_layout']/*[@text = 'Save']"),
                "Cant find 'Save' button at bottom of the page", 5);
        waitForElementAndClick(By.xpath("//*[@resource-id = 'org.wikipedia:id/snackbar_action'][@text = 'ADD TO LIST']"),
                "Cant find 'ADD TO LIST' button after pressed Save button", 5);

        By gotItXpathLocator = By.xpath("//*[@resource-id = 'org.wikipedia:id/onboarding_button'][@text = 'GOT IT']");
        if (isElementPresent(gotItXpathLocator, 5))
            waitForElementAndClick(gotItXpathLocator,
                    "Cant find 'GOT IT' button", 5);

        By neededListNameXpathLocator = By.xpath("//*[@resource-id = 'org.wikipedia:id/item_title'][@text = '" + listName + "']");
        if (isElementPresent(neededListNameXpathLocator, 2)) {
            waitForElementAndClick(neededListNameXpathLocator,
                    "Cant click at listName " + listName, 5);
        } else {
            waitForElementAndSendKeys(By.xpath("//*[@resource-id = 'org.wikipedia:id/text_input'][@text = 'Name of this list']"),
                    listName, "Cant fill the 'Name of this list' string", 5);
            waitForElementAndClick(By.xpath("//android.widget.Button[@text = 'OK']"), "Cant find 'OK' button", 5);

        }

        String statusText = waitForElementAndGetAttribute(By.xpath("//*[@resource-id = 'org.wikipedia:id/snackbar_text']"),
                "text", "Cant get status text after saving the list ", 5);

        assertEquals("Status message after saving article to list is not equals expected",
                "Moved " + articleName + " to " + listName + ".", statusText);
    }

    private void goToMainPage() {
        waitForElementAndClick(By.xpath("//android.widget.ImageView[@content-desc='More options']"), "cant find 'More options' button", 5);
        waitForElementAndClick(By.id("org.wikipedia:id/overflow_feed"), "cant find 'Explore' button", 5);
        driver.navigate().back();
    }

    private void goToMyLists() {
        waitForElementAndClick(By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"), "cant find 'My lists' button", 5);
    }

    private void openSavedList(String listName) {
        goToMyLists();
        waitTimeOut(1);
        waitForElementAndClick(By.xpath("//*[@resource-id = 'org.wikipedia:id/item_title'][@text = '" + listName + "']"),
                "cant find list with name " + listName, 5);
    }

    private List<WebElement> getCurrentArticlesNamesElements() {
        waitTimeOut(1);
        return (List<WebElement>) driver.findElements(By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_title']"));
    }

    private List<String> getCurrentArticlesNames() {
        List<WebElement> currentArticlesElements = getCurrentArticlesNamesElements();
        List<String> currentArticles = new ArrayList<>();
        for (WebElement webElement : currentArticlesElements) {
            currentArticles.add(webElement.getAttribute("text"));
        }

        return currentArticles;
    }

    private int deleteAnyArticle() {
        List<WebElement> articles = getCurrentArticlesNamesElements();

        int randomNum = ThreadLocalRandom.current().nextInt(1, articles.size() + 1);
        String articleNameToDelete = articles.get(randomNum - 1).getAttribute("text");
        swipeElementToLeft(By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_title'][@text = '" + articleNameToDelete + "']"),
                "Cant swipe article");

        String statusText = waitForElementAndGetAttribute(By.xpath("//*[@resource-id = 'org.wikipedia:id/snackbar_text']"),
                "text", "Cant get status text after saving the list ", 5);

        assertEquals("Status message after saving article to list is not equals expected",
                articleNameToDelete + " removed from list", statusText);

        return randomNum;
    }

    private void waitTimeOut(int sec) {
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