package lib.ui;

import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

import static junit.framework.TestCase.assertEquals;

abstract public class ArticlePageObject extends MainPageObject {

    protected static String
            SAVE_BUTTON,
            CLOSE_SYNC_MESSAGE,
            ADD_TO_LIST_BUTTON,
            DELETE_FROM_MY_LISTS,
            GOT_IT_BUTTON,
            NEW_LIST_NAME_INPUT,
            OK_BUTTON,
            STATUS_TEXT,
            NEEDED_LIST_NAME_XPATH_TPL,
            ARTICLE_TITLE_WITH_NAME_TPL;

    public ArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void addToList(String articleName, String listName) {
        waitTimeOut(2);
        waitForElementAndClick(SAVE_BUTTON, "Cant find 'Save' button at bottom of the page", 5);
        waitForElementAndClick(ADD_TO_LIST_BUTTON, "Cant find 'ADD TO LIST' button after pressed Save button", 5);

        if (isElementPresent(GOT_IT_BUTTON, 5))
            waitForElementAndClick(GOT_IT_BUTTON, "Cant find 'GOT IT' button", 5);

        String neededListNameXpathLocator = String.format(NEEDED_LIST_NAME_XPATH_TPL, listName);
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

    public void addArticlesToMySaved() {
        if (Platform.getInstance().isMw()) {
            removeArticleFromSavedIfAdded();
        }
        this.waitForElementAndClick(SAVE_BUTTON, "Cannot find save article button", 5);

        if (Platform.getInstance().isIOS()) {
            if (isElementPresent(CLOSE_SYNC_MESSAGE, 2))
                this.waitForElementAndClick(CLOSE_SYNC_MESSAGE, "Cannot find button to close 'Sync your saved articles?' window", 5);
        }
    }

    public void removeArticleFromSavedIfAdded() {
        if (isElementPresent(DELETE_FROM_MY_LISTS, 2)) {
            waitForElementAndClick(DELETE_FROM_MY_LISTS, "cant delete article", 5);
            waitForElementPresent(SAVE_BUTTON, "cant find button to add article to list", 5);
        }
    }

    private String getStatusText() {
        return waitForElementAndGetAttribute(STATUS_TEXT, "text", "Cant get status text after saving the list ", 5);
    }

    public void assertArticleTitleIsPresent(String articleTitle) {
        assertElementPresent(String.format(ARTICLE_TITLE_WITH_NAME_TPL, articleTitle));
    }
}