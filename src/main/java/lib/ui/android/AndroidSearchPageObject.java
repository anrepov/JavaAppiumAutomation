package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;

public class AndroidSearchPageObject extends SearchPageObject {

    static {
        SEARCH_ELEMENT = "className:android.widget.TextView";
        SEARCH_LINE = "id:org.wikipedia:id/search_src_text";
        SEARCH_RESULTS = "xpath://*[@resource-id = 'org.wikipedia:id/search_results_list']/*[@class='android.view.ViewGroup']";
        SEARCH_CANCEL_BUTTON = "id:org.wikipedia:id/search_close_btn";
        SEARCH_RESULT_BY_INDEX_TPL = "xpath://*[@resource-id = 'org.wikipedia:id/search_results_list']" +
                "/*[@class='android.view.ViewGroup']" +
                "[@index = '%s']" +
                "/*[@resource-id = 'org.wikipedia:id/page_list_item_title']";
        SEARCH_RESULT_BY_TEXT_TPL = "xpath://*[@resource-id = 'org.wikipedia:id/search_results_list']" +
                "/*[@class='android.view.ViewGroup']" +
                "/*[@resource-id = 'org.wikipedia:id/page_list_item_title']" +
                "[@text = '%s']";
        SEARCH_RESULT_BY_TEXT_AND_DESCRIPTION_TPL = "xpath://*[@resource-id = 'org.wikipedia:id/search_results_list']" +
                "//*[@resource-id = 'org.wikipedia:id/page_list_item_title' and ../../*[@class='android.view.ViewGroup'] and @text = '%s' " +
                "and following-sibling::*[@resource-id = 'org.wikipedia:id/page_list_item_description' and @text = '%s']]";
    }

    public AndroidSearchPageObject(AppiumDriver driver) {
        super(driver);
    }


}
