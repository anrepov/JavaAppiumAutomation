package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class ArticleTests extends CoreTestCase {

    @Test
    public void testAssertArticleHasTitle() {
        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        String articleTitle = "Java (programming language)";
        searchPageObject.searchText("Java");
        searchPageObject.selectResultWithText(articleTitle);

        articlePageObject.assertArticleTitleIsPresent(articleTitle);
    }
}