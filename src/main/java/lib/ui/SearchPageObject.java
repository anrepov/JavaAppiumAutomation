package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static junit.framework.TestCase.assertTrue;

abstract public class SearchPageObject extends MainPageObject {

    protected static String
            SEARCH_ELEMENT,
            SEARCH_LINE,
            SEARCH_RESULTS,
            SEARCH_CANCEL_BUTTON,
            SEARCH_RESULT_BY_INDEX_TPL,
            SEARCH_RESULT_BY_TEXT_TPL,
            SEARCH_RESULT_BY_TEXT_AND_DESCRIPTION_TPL;

    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    public void searchText(String text) {
        waitForElementAndClick(SEARCH_ELEMENT, "cant find search field", 5);
        waitForElementAndSendKeys(SEARCH_LINE, text, "cant find search line", 5);
    }

    public List<WebElement> getVisibleSearchResults() {
        waitForElementPresent(SEARCH_RESULTS, "cant find search results");
        return driver.findElements(getLocatorByString(SEARCH_RESULTS));
    }

    public void cancelSearch() {
        waitForElementAndClick(SEARCH_CANCEL_BUTTON, "cant find X button", 5);
        waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "X button still present", 5);
    }

    public void assertSearchResultsHasText(String text) {
        List<WebElement> searchResults = getVisibleSearchResults();
        for (int i = 0; i < searchResults.size(); i++) {
            WebElement result = driver.findElement(getLocatorByString(String.format(SEARCH_RESULT_BY_INDEX_TPL, i)));
            assertTrue(String.format("Текст в заголовке %s результата, равный %s, не совпадает с ожидаемым %s", i + 1, result.getText(), text),
                    result.getText().contains(text));
        }
    }

    public void selectResultWithText(String text) {
        waitForElementAndClick(String.format(SEARCH_RESULT_BY_TEXT_TPL, text), "cant find result with text " + text, 5);
    }

    public void assertSearchLineHasSuggest() {
        assertElementHasText(SEARCH_ELEMENT, "Search Wikipedia", "Строка поиска не содержит ожидаемый текст");
    }

    public void waitForElementByTitleAndDescription(String title, String description) {
        String xpath = String.format(SEARCH_RESULT_BY_TEXT_AND_DESCRIPTION_TPL, title, description);
        waitForElementPresent(xpath, String.format("Cant find search result with title %s and description %s", title, description), 5);
    }
}
