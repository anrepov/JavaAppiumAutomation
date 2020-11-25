package lib.ui.mobile_web;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWSearchPageObject extends SearchPageObject {

    static {
        SEARCH_ELEMENT = "xpath://button[@id = 'searchIcon']";
        SEARCH_LINE = "xpath://input[@class = 'search']";
        SEARCH_RESULTS = "xpath://ul[contains(@class, 'page-list')]/li";
        SEARCH_CANCEL_BUTTON = "xpath://div[@class = 'header-action']/button[contains(@class, 'cancel')]";
        SEARCH_RESULT_BY_INDEX_TPL = "xpath://ul[contains(@class, 'page-list')]/li[%s]";
        SEARCH_RESULT_BY_TEXT_TPL = "xpath://li[contains(@title, '%s')]";
        SEARCH_RESULT_BY_TEXT_AND_DESCRIPTION_TPL = "//li[contains(@title, '%s')]//div[text() = '%s']";
    }

    public MWSearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}