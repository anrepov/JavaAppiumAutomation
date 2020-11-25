package lib.ui.mobile_web;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWArticlePageObject extends ArticlePageObject {

    static {
        SAVE_BUTTON = "xpath://*[text()='Watch']/..";
        DELETE_FROM_MY_LISTS = "xpath://*[text()='Unwatch']/..";
        ARTICLE_TITLE_WITH_NAME_TPL = "xpath://div[@class='page-heading']/h1[text()='%s']";
    }

    public MWArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }
}