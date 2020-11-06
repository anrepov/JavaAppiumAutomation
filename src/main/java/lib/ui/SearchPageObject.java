package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static junit.framework.TestCase.assertTrue;

public class SearchPageObject extends MainPageObject {

    private static final String
            SEARCH_ELEMENT = "className:android.widget.TextView",
            SEARCH_LINE = "id:org.wikipedia:id/search_src_text",
            SEARCH_RESULTS = "xpath://*[@resource-id = 'org.wikipedia:id/search_results_list']/*[@class='android.view.ViewGroup']",
            SEARCH_CANCEL_BUTTON = "id:org.wikipedia:id/search_close_btn",
            SEARCH_RESULT_BY_INDEX_TPL = "//*[@resource-id = 'org.wikipedia:id/search_results_list']" +
                    "/*[@class='android.view.ViewGroup']" +
                    "[@index = '%s']" +
                    "/*[@resource-id = 'org.wikipedia:id/page_list_item_title']",
            SEARCH_RESULT_BY_TEXT_TPL = "//*[@resource-id = 'org.wikipedia:id/search_results_list']" +
                    "/*[@class='android.view.ViewGroup']" +
                    "/*[@resource-id = 'org.wikipedia:id/page_list_item_title']" +
                    "[@text = '%s']",
            SEARCH_RESULT_BY_TEXT_AND_DESCRIPTION_TPL = "//*[@resource-id = 'org.wikipedia:id/search_results_list']" +
                    "//*[@resource-id = 'org.wikipedia:id/page_list_item_title' and ../../*[@class='android.view.ViewGroup'] and @text = '%s' " +
                    "and following-sibling::*[@resource-id = 'org.wikipedia:id/page_list_item_description' and @text = '%s']]";

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
        String xpath = getSearchResultXpathByTextAndDescription(title, description);
        waitForElementPresent(xpath, String.format("Cant find search result with title %s and description %s", title, description), 5);
    }

    /*  TEMPLATES METHODS */
    private String getSearchResultXpathByTextAndDescription(String title, String description) {
        return String.format(SEARCH_RESULT_BY_TEXT_AND_DESCRIPTION_TPL, title, description);
    }
    /*  TEMPLATES METHODS */
}
