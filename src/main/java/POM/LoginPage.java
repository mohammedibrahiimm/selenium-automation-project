package POM;

import Utils.ActionUtils;
import Utils.LogUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private static final By USERNAME_FIELD = By.id("user-name");
    private static final By PASSWORD_FIELD = By.id("password");
    private static final By LOGIN_BUTTON   = By.id("login-button");
    private static final By ERROR_MESSAGE  = By.cssSelector("[data-test='error']");

    private final WebDriver driver;
    private final ActionUtils action;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.action = new ActionUtils(driver);
    }

    /** Valid login flow — returns the next page. */
    public HomePage login(String user, String pass) {
        action.sendKeys(USERNAME_FIELD, user);
        action.sendKeys(PASSWORD_FIELD, pass);
        submitLogin();
        LogUtils.info("Login submitted for user: " + user);
        return new HomePage(driver);
    }

    /** Login attempt with an empty password field — stays on login page. */
    public LoginPage loginWithEmptyPassword(String user) {
        action.sendKeys(USERNAME_FIELD, user);
        action.sendKeys(PASSWORD_FIELD, "");
        submitLogin();
        LogUtils.info("Login submitted with empty password");
        return this;
    }

    /** Login attempt with an empty username field — stays on login page. */
    public LoginPage loginWithEmptyUsername(String pass) {
        action.sendKeys(USERNAME_FIELD, "");
        action.sendKeys(PASSWORD_FIELD, pass);
        submitLogin();
        LogUtils.info("Login submitted with empty username");
        return this;
    }

    public String getErrorMessage() {
        return action.getText(ERROR_MESSAGE);
    }

    private void submitLogin() {
        action.click(LOGIN_BUTTON);
    }
}