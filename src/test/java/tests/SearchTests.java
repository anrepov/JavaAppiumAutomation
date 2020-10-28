package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class SearchTests extends CoreTestCase {

    @Test
    public void testCheckSearchFieldText() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.assertSearchLineHasSuggest();
    }

    @Test
    public void testAssertResultsByTextNotEmptyThenClear() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.searchText("Java");
        assertTrue("Search results is empty", searchPageObject.getVisibleSearchResults().size() > 1);
        searchPageObject.cancelSearch();
    }

    @Test
    public void testAssertResultsByTextHasThisText() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        String textForSearch = "Java";
        searchPageObject.searchText(textForSearch);
        searchPageObject.assertSearchResultsHasText(textForSearch);
    }
}