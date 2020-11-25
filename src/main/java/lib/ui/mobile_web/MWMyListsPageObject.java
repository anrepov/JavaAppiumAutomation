package lib.ui.mobile_web;

import lib.ui.MyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWMyListsPageObject extends MyListsPageObject {

    static {
        CURRENT_ARTICLES_NAMES = "xpath://ul[contains(@class, 'watchlist-page-list')]/li//h3";
        SAVED_LIST_ELEMENT_XPATH_TPL = "xpath://ul[contains(@class, 'watchlist-page-list')]/li//h3[text() = '%s']/../../a[contains(@class, 'watch-this-article watched')]";
    }

    public MWMyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}