package lib.ui.android;

import lib.ui.MyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidMyListsPageObject extends MyListsPageObject {

    static {
        CURRENT_ARTICLES_NAMES = "xpath://*[@resource-id = 'org.wikipedia:id/page_list_item_title']";
        STATUS_TEXT = "xpath://*[@resource-id = 'org.wikipedia:id/snackbar_text']";
        SAVED_LIST_NAME_XPATH_TPL = "xpath://*[@resource-id = 'org.wikipedia:id/item_title'][@text = '%s']";
        SAVED_LIST_ELEMENT_XPATH_TPL = "xpath://*[@resource-id = 'org.wikipedia:id/page_list_item_title'][@text = '%s']";
    }

    public AndroidMyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
