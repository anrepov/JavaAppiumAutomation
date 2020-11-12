package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationPageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationPageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

import java.util.List;

public class MyListsTests extends CoreTestCase {

    @Test
    public void testSaveTwoArticlesToListThenDeleteOne() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
        NavigationPageObject navigationPageObject = NavigationPageObjectFactory.get(driver);

        String articleTitle;
        String listName = "testList";
        searchPageObject.searchText("Java");

        if (Platform.getInstance().isAndroid()) articleTitle = "Java (programming language)";
        else articleTitle = "programming language";
        searchPageObject.selectResultWithText(articleTitle);

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addToList(articleTitle, listName);
            driver.navigate().back();
        } else {
            articlePageObject.addArticlesToMySaved();
            navigationPageObject.goToMainPage();
            searchPageObject.searchText("Java");
        }

        if (Platform.getInstance().isAndroid()) articleTitle = "Java";
        else articleTitle = "Island of Indonesia";
        searchPageObject.selectResultWithText(articleTitle);

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addToList(articleTitle, listName);
            driver.navigate().back();
        } else {
            articlePageObject.addArticlesToMySaved();
        }

        navigationPageObject.goToMainPage();

        if (Platform.getInstance().isAndroid())
            myListsPageObject.openSavedList(listName);
        else
            navigationPageObject.goToMyListsFromMainPage();

        List<String> articlesBeforeDelete = myListsPageObject.getCurrentArticlesNames();

        int deletedArticleIndex = myListsPageObject.deleteAnyArticle();
        String deletedArticleName = articlesBeforeDelete.get(deletedArticleIndex - 1);
        List<String> articlesAfterDelete = myListsPageObject.getCurrentArticlesNames();

        articlesBeforeDelete.removeAll(articlesAfterDelete);
        assertEquals("fwef", articlesBeforeDelete.get(0), deletedArticleName);
    }
}