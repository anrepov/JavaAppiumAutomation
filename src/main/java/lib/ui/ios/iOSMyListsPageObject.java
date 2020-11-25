package lib.ui.ios;

import lib.ui.MyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class iOSMyListsPageObject extends MyListsPageObject {

    static {
        CURRENT_ARTICLES_NAMES = "className:XCUIElementTypeLink";
        SAVED_LIST_ELEMENT_XPATH_TPL = "xpath://XCUIElementTypeLink[contains(@name,'%s')]";
    }

    public iOSMyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
