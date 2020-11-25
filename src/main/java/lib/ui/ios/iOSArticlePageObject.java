package lib.ui.ios;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class iOSArticlePageObject extends ArticlePageObject {

    static {
        SAVE_BUTTON = "id:Save for later";
        ARTICLE_TITLE_WITH_NAME_TPL = "id:%s";
        CLOSE_SYNC_MESSAGE = "id:places auth close";
    }

    public iOSArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }

}
