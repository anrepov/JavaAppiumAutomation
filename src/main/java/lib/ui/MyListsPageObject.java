package lib.ui;

import lib.Platform;
import lib.ui.factories.NavigationPageObjectFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

import static junit.framework.TestCase.assertEquals;

public abstract class MyListsPageObject extends MainPageObject {

    protected static String
            CURRENT_ARTICLES_NAMES,
            STATUS_TEXT,
            SAVED_LIST_NAME_XPATH_TPL,
            SAVED_LIST_ELEMENT_XPATH_TPL;

    public MyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void openSavedList(String listName) {
        NavigationPageObject navigationPageObject = NavigationPageObjectFactory.get(driver);

        navigationPageObject.goToMyListsFromMainPage();
        waitTimeOut(1);
        waitForElementAndClick(String.format(SAVED_LIST_NAME_XPATH_TPL, listName), "cant find list with name " + listName, 5);
    }

    public int deleteAnyArticle() {
        List<WebElement> articles = getCurrentArticlesNamesElements();

        int randomNum = ThreadLocalRandom.current().nextInt(1, articles.size() + 1);

        String articleNameToDelete = getArticleNameByPlatform(articles.get(randomNum - 1));
        System.out.println("article to delete " + articleNameToDelete);

        if (Platform.getInstance().isIOS()) {
            String[] articleNameToDeleteMass = articleNameToDelete.split(Pattern.quote("\n"));
            articleNameToDelete = articleNameToDeleteMass[articleNameToDeleteMass.length - 1];
        }
        if (Platform.getInstance().isMw()) {
            System.out.println("el " + String.format(SAVED_LIST_ELEMENT_XPATH_TPL, articleNameToDelete));
            waitForElementAndClick(String.format(SAVED_LIST_ELEMENT_XPATH_TPL, articleNameToDelete),
                    "cant click unwatch at article " + articleNameToDelete, 5);
        } else {
            swipeElementToLeft(String.format(SAVED_LIST_ELEMENT_XPATH_TPL, articleNameToDelete), "Cant swipe article");
        }

        if (Platform.getInstance().isIOS())
            this.clickElementToTheRightUpperCorner(String.format(SAVED_LIST_ELEMENT_XPATH_TPL, articleNameToDelete), "cant find saved article");

        if (Platform.getInstance().isAndroid())
            assertEquals("Status message after saving article to list is not equals expected",
                    articleNameToDelete + " removed from list", getStatusText());

        if (Platform.getInstance().isMw())
            driver.navigate().refresh();

        return randomNum;
    }

    public List<String> getCurrentArticlesNames() {
        List<WebElement> currentArticlesElements = getCurrentArticlesNamesElements();
        List<String> currentArticles = new ArrayList<>();

        for (WebElement webElement : currentArticlesElements) {
            currentArticles.add(getArticleNameByPlatform(webElement));
        }
        return currentArticles;
    }

    private List<WebElement> getCurrentArticlesNamesElements() {
        waitTimeOut(1);
        return driver.findElements(getLocatorByString(CURRENT_ARTICLES_NAMES));
    }

    private String getArticleNameByPlatform(WebElement webElement) {
        if (Platform.getInstance().isIOS()) return webElement.getAttribute("name");
        else if (Platform.getInstance().isAndroid()) return webElement.getAttribute("text");
        else return webElement.getText();
    }

    private String getStatusText() {
        return waitForElementAndGetAttribute(STATUS_TEXT, "text", "Cant get status text after saving the list ", 5);
    }
}