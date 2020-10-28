package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static junit.framework.TestCase.assertEquals;

public class MyListsPageObject extends MainPageObject {

    private static final By
            CURRENT_ARTICLES_NAMES = By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_title']"),
            STATUS_TEXT = By.xpath("//*[@resource-id = 'org.wikipedia:id/snackbar_text']");
    private static final String
            SAVED_LIST_NAME_XPATH_TPL = "//*[@resource-id = 'org.wikipedia:id/item_title'][@text = '%s']",
            SAVED_LIST_ELEMENT_XPATH_TPL = "//*[@resource-id = 'org.wikipedia:id/page_list_item_title'][@text = '%s']";

    public MyListsPageObject(AppiumDriver driver) {
        super(driver);
    }

    public void openSavedList(String listName) {
        NavigationPageObject navigationPageObject = new NavigationPageObject(driver);

        navigationPageObject.goToMyListsFromMainPage();
        waitTimeOut(1);
        waitForElementAndClick(By.xpath(String.format(SAVED_LIST_NAME_XPATH_TPL, listName)), "cant find list with name " + listName, 5);
    }

    public int deleteAnyArticle() {
        List<WebElement> articles = getCurrentArticlesNamesElements();

        int randomNum = ThreadLocalRandom.current().nextInt(1, articles.size() + 1);
        String articleNameToDelete = articles.get(randomNum - 1).getAttribute("text");
        swipeElementToLeft(By.xpath(String.format(SAVED_LIST_ELEMENT_XPATH_TPL, articleNameToDelete)), "Cant swipe article");

        assertEquals("Status message after saving article to list is not equals expected",
                articleNameToDelete + " removed from list", getStatusText());
        return randomNum;
    }

    public List<String> getCurrentArticlesNames() {
        List<WebElement> currentArticlesElements = getCurrentArticlesNamesElements();
        List<String> currentArticles = new ArrayList<>();
        for (WebElement webElement : currentArticlesElements) {
            currentArticles.add(webElement.getAttribute("text"));
        }
        return currentArticles;
    }

    private List<WebElement> getCurrentArticlesNamesElements() {
        waitTimeOut(1);
        return (List<WebElement>) driver.findElements(CURRENT_ARTICLES_NAMES);
    }

    private String getStatusText() {
        return waitForElementAndGetAttribute(STATUS_TEXT, "text", "Cant get status text after saving the list ", 5);
    }
}