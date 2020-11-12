package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;

public class iOSArticlePageObject extends ArticlePageObject {

    static {
        SAVE_BUTTON = "id:Save for later";
        ARTICLE_TITLE_WITH_NAME_TPL = "id:%s";
        CLOSE_SYNC_MESSAGE = "id:places auth close";
    }

    public iOSArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

}
