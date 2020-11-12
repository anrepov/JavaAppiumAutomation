package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;
import org.openqa.selenium.WebElement;

import java.util.List;

import static junit.framework.TestCase.assertTrue;

public class iOSSearchPageObject extends SearchPageObject {

    static {
        SEARCH_ELEMENT = "xpath://XCUIElementTypeSearchField[@name='Search Wikipedia']";
        SEARCH_LINE = "xpath://XCUIElementTypeSearchField[not(@name='Search Wikipedia')]";
        SEARCH_RESULTS = "xpath://XCUIElementTypeCollectionView/XCUIElementTypeCell";
        SEARCH_CANCEL_BUTTON = "id:Close";
        SEARCH_RESULT_BY_INDEX_TPL = "xpath://*[@type = 'XCUIElementTypeCollectionView']" +
                "/*[@type = 'XCUIElementTypeCell'][%s]" +
                "/*[@type = 'XCUIElementTypeLink']";
        SEARCH_RESULT_BY_TEXT_TPL = "xpath://*[contains(@name, '%s')]";
        SEARCH_RESULT_BY_TEXT_AND_DESCRIPTION_TPL = "xpath://*[contains(@name, '%s')][contains(@name, '%s')]";
    }

    public iOSSearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    @Override
    public void assertSearchResultsHasText(String text) {
        List<WebElement> searchResults = getVisibleSearchResults();
        for (int i = 0; i < searchResults.size(); i++) {
            WebElement result = driver.findElement(getLocatorByString(String.format(SEARCH_RESULT_BY_INDEX_TPL, i + 1)));
            assertTrue(String.format("Текст в заголовке %s результата, равный %s, не совпадает с ожидаемым %s", i + 1, result.getText(), text),
                    result.getText().contains(text));
        }
    }

}
