package lib.ui;

import org.openqa.selenium.remote.RemoteWebDriver;

public class AuthorizationPageObject extends MainPageObject {

    private static final String
            LOGIN_BUTTON = "xpath://span[text()='Log in']/..",
            LOGIN_INPUT = "xpath://input[@name='wpName']",
            PASSWORD_INPUT = "xpath://input[@name='wpPassword']",
            SUBMIT_BUTTON = "xpath://button[@name='wploginattempt']";

    public AuthorizationPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void login(String login, String password) {
        waitForElementAndClick(LOGIN_BUTTON, "Cant find auth button", 5);
        waitTimeOut(1);
        waitForElementAndSendKeys(LOGIN_INPUT, login, "Cant find and fill login input", 5);
        waitForElementAndSendKeys(PASSWORD_INPUT, password, "Cant find and fill password input", 5);
        waitForElementAndClick(SUBMIT_BUTTON, "Cant find submit button", 5);
    }
}