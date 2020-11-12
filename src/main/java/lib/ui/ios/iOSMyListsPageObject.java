package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListsPageObject;

public class iOSMyListsPageObject extends MyListsPageObject {

    static {
        CURRENT_ARTICLES_NAMES = "className:XCUIElementTypeLink";
        SAVED_LIST_ELEMENT_XPATH_TPL = "xpath://XCUIElementTypeLink[contains(@name,'%s')]";
    }

    public iOSMyListsPageObject(AppiumDriver driver) {
        super(driver);
    }
}
