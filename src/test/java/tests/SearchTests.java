package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class SearchTests extends CoreTestCase {

    @Test
    public void testCheckSearchFieldText() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.assertSearchLineHasSuggest();
    }

    @Test
    public void testAssertResultsByTextNotEmptyThenClear() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.searchText("Java");
        assertTrue("Search results is empty", searchPageObject.getVisibleSearchResults().size() > 1);
        searchPageObject.cancelSearch();
    }

    @Test
    public void testAssertResultsByTextHasThisText() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        String textForSearch = "Java";
        searchPageObject.searchText(textForSearch);
        searchPageObject.assertSearchResultsHasText(textForSearch);
    }

    @Test
    public void testFindSearchResultWithTitleAndDescription() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.searchText("Java");

        Map<String, String> expectedSearchResults = new HashMap<>();
        expectedSearchResults.put("Java", "Indonesian island");
        expectedSearchResults.put("JavaScript", "High-level programming language");
        expectedSearchResults.put("Java (programming language)", "Object-oriented programaming language");

        expectedSearchResults.forEach(searchPageObject::waitForElementByTitleAndDescription);
    }
}