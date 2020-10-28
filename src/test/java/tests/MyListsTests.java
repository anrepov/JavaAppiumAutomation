package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationPageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;

import java.util.List;

public class MyListsTests extends CoreTestCase {

    @Test
    public void testSaveTwoArticlesToListThenDeleteOne() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        MyListsPageObject myListsPageObject = new MyListsPageObject(driver);
        NavigationPageObject navigationPageObject = new NavigationPageObject(driver);

        String articleTitle;
        String listName = "testList";
        searchPageObject.searchText("Java");

        articleTitle = "Java (programming language)";
        searchPageObject.selectResultWithText(articleTitle);
        articlePageObject.addToList(articleTitle, listName);

        driver.navigate().back();

        articleTitle = "Java";
        searchPageObject.selectResultWithText(articleTitle);
        articlePageObject.addToList(articleTitle, listName);

        navigationPageObject.goToMainPage();
        myListsPageObject.openSavedList(listName);
        List<String> articlesBeforeDelete = myListsPageObject.getCurrentArticlesNames();

        int deletedArticleIndex = myListsPageObject.deleteAnyArticle();
        String deletedArticleName = articlesBeforeDelete.get(deletedArticleIndex - 1);
        List<String> articlesAfterDelete = myListsPageObject.getCurrentArticlesNames();

        articlesBeforeDelete.removeAll(articlesAfterDelete);
        assertEquals("fwef", articlesBeforeDelete.get(0), deletedArticleName);
    }
}