package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class ArticleTests extends CoreTestCase {

    @Test
    public void testAssertArticleHasTitle() {
        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        String articleTitle = "Java (programming language)";
        searchPageObject.searchText("Java");
        searchPageObject.selectResultWithText(articleTitle);

        articlePageObject.assertArticleTitleIsPresent(articleTitle);
    }
}