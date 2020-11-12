package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;

public class AndroidArticlePageObject extends ArticlePageObject {

    static {
        SAVE_BUTTON = "xpath://*[@resource-id = 'org.wikipedia:id/page_actions_tab_layout']/*[@text = 'Save']";
        ADD_TO_LIST_BUTTON = "xpath://*[@resource-id = 'org.wikipedia:id/snackbar_action'][@text = 'ADD TO LIST']";
        GOT_IT_BUTTON = "xpath://*[@resource-id = 'org.wikipedia:id/onboarding_button'][@text = 'GOT IT']";
        NEW_LIST_NAME_INPUT = "xpath://*[@resource-id = 'org.wikipedia:id/text_input'][@text = 'Name of this list']";
        OK_BUTTON = "xpath://android.widget.Button[@text = 'OK']";
        STATUS_TEXT = "xpath://*[@resource-id = 'org.wikipedia:id/snackbar_text']";
        NEEDED_LIST_NAME_XPATH_TPL = "xpath://*[@resource-id = 'org.wikipedia:id/item_title'][@text = '%s']";
        ARTICLE_TITLE_WITH_NAME_TPL = "xpath://android.view.View[@content-desc='%s']";
    }

    public AndroidArticlePageObject(AppiumDriver driver) {
        super(driver);
    }
}
