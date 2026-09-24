package POM;

import Utils.LogUtils;
import Utils.ActionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private static final By USERNAME_FIELD = By.id("user-name");
    private static final By PASSWORD_FIELD = By.id("password");
    private static final By LOGIN_BUTTON   = By.id("login-button");

    private final WebDriver driver;
    private final ActionUtils action;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.action = new ActionUtils(driver);
    }

    public HomePage login(String user, String pass) {
        action.sendKeys(USERNAME_FIELD, user);
        action.sendKeys(PASSWORD_FIELD, pass);
        action.click(LOGIN_BUTTON);
        LogUtils.info("Login submitted for user: " + user);
        return new HomePage(driver);   // ✅ next page
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}