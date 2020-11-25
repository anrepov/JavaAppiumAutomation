package lib.ui.ios;

import lib.ui.NavigationPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class iOSNavigationPageObject extends NavigationPageObject {

    static {
        EXPLORE_BUTTON = "id:Explore";
        MY_LISTS_BUTTON = "id:Saved";
        BACK_BUTTON = "id:Back";
    }

    public iOSNavigationPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    @Override
    public void goToMainPage() {
        waitForElementAndClick(BACK_BUTTON, "cant find 'Back' button", 5);
    }
}
