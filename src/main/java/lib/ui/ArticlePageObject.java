package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

import static junit.framework.TestCase.assertEquals;

public class ArticlePageObject extends MainPageObject {

    private static final By
            SAVE_BUTTON = By.xpath("//*[@resource-id = 'org.wikipedia:id/page_actions_tab_layout']/*[@text = 'Save']"),
            ADD_TO_LIST_BUTTON = By.xpath("//*[@resource-id = 'org.wikipedia:id/snackbar_action'][@text = 'ADD TO LIST']"),
            GOT_IT_BUTTON = By.xpath("//*[@resource-id = 'org.wikipedia:id/onboarding_button'][@text = 'GOT IT']"),
            NEW_LIST_NAME_INPUT = By.xpath("//*[@resource-id = 'org.wikipedia:id/text_input'][@text = 'Name of this list']"),
            OK_BUTTON = By.xpath("//android.widget.Button[@text = 'OK']"),
            STATUS_TEXT = By.xpath("//*[@resource-id = 'org.wikipedia:id/snackbar_text']");
    private static final String
            NEEDED_LIST_NAME_XPATH_TPL = "//*[@resource-id = 'org.wikipedia:id/item_title'][@text = '%s']",
            ARTICLE_TITLE_WITH_NAME_TPL = "//android.view.View[@content-desc='%s']";

    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    public void addToList(String articleName, String listName) {
        waitTimeOut(2);
        waitForElementAndClick(SAVE_BUTTON, "Cant find 'Save' button at bottom of the page", 5);
        waitForElementAndClick(ADD_TO_LIST_BUTTON, "Cant find 'ADD TO LIST' button after pressed Save button", 5);

        if (isElementPresent(GOT_IT_BUTTON, 5))
            waitForElementAndClick(GOT_IT_BUTTON, "Cant find 'GOT IT' button", 5);

        By neededListNameXpathLocator = By.xpath(String.format(NEEDED_LIST_NAME_XPATH_TPL, listName));
        if (isElementPresent(neededListNameXpathLocator, 2)) {
            waitForElementAndClick(neededListNameXpathLocator,
                    "Cant click at listName " + listName, 5);
        } else {
            waitForElementAndSendKeys(NEW_LIST_NAME_INPUT, listName, "Cant fill the 'Name of this list' string", 5);
            waitForElementAndClick(OK_BUTTON, "Cant find 'OK' button", 5);
        }

        assertEquals("Status message after saving article to list is not equals expected",
                "Moved " + articleName + " to " + listName + ".", getStatusText());
    }

    private String getStatusText() {
        return waitForElementAndGetAttribute(STATUS_TEXT, "text", "Cant get status text after saving the list ", 5);
    }

    public void assertArticleTitleIsPresent(String articleTitle) {
        assertElementPresent(By.xpath(String.format(ARTICLE_TITLE_WITH_NAME_TPL, articleTitle)));
    }
}