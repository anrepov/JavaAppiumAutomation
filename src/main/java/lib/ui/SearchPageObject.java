package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static junit.framework.TestCase.assertTrue;

public class SearchPageObject extends MainPageObject {

    private static final By
            SEARCH_ELEMENT = By.className("android.widget.TextView"),
            SEARCH_LINE = By.id("org.wikipedia:id/search_src_text"),
            SEARCH_RESULTS = By.xpath("//*[@resource-id = 'org.wikipedia:id/search_results_list']/*[@class='android.view.ViewGroup']"),
            SEARCH_CANCEL_BUTTON = By.id("org.wikipedia:id/search_close_btn");
    private static final String SEARCH_RESULT_BY_INDEX_TPL = "//*[@resource-id = 'org.wikipedia:id/search_results_list']" +
            "/*[@class='android.view.ViewGroup']" +
            "[@index = '%s']" +
            "/*[@resource-id = 'org.wikipedia:id/page_list_item_title']";
    private static final String SEARCH_RESULT_BY_TEXT_TPL = "//*[@resource-id = 'org.wikipedia:id/search_results_list']" +
            "/*[@class='android.view.ViewGroup']" +
            "/*[@resource-id = 'org.wikipedia:id/page_list_item_title']" +
            "[@text = '%s']";

    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    public void searchText(String text) {
        waitForElementAndClick(SEARCH_ELEMENT, "cant find search field", 5);
        waitForElementAndSendKeys(SEARCH_LINE, text, "cant find search line", 5);
    }

    public List<WebElement> getVisibleSearchResults() {
        waitForElementPresent(SEARCH_RESULTS, "cant find search results");
        return driver.findElements(SEARCH_RESULTS);
    }

    public void cancelSearch() {
        waitForElementAndClick(SEARCH_CANCEL_BUTTON, "cant find X button", 5);
        waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "X button still present", 5);
    }

    public void assertSearchResultsHasText(String text) {
        List<WebElement> searchResults = getVisibleSearchResults();
        for (int i = 0; i < searchResults.size(); i++) {
            WebElement result = driver.findElement(By.xpath(String.format(SEARCH_RESULT_BY_INDEX_TPL, i)));
            assertTrue(String.format("Текст в заголовке %s результата, равный %s, не совпадает с ожидаемым %s", i + 1, result.getText(), text),
                    result.getText().contains(text));
        }
    }

    public void selectResultWithText(String text) {
        waitForElementAndClick(By.xpath(String.format(SEARCH_RESULT_BY_TEXT_TPL, text)), "cant find result with text " + text, 5);
    }

    public void assertSearchLineHasSuggest() {
        assertElementHasText(SEARCH_ELEMENT, "Search Wikipedia", "Строка поиска не содержит ожидаемый текст");
    }
}
