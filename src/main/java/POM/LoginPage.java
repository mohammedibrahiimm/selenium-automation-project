package POM;

import Utils.LogUtils;
import Utils.actionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

public class LoginPage {
    private static final Logger log = LoggerFactory.getLogger(LoginPage.class);
    private WebDriver driver;
    private final By username = By.id("user-name");
    private final By password = By.id("password");
    private final By loginBtn = By.id("login-button");
    private actionUtils action;

    public LoginPage(WebDriver driver){
        this.driver = driver;
        this.action = new actionUtils(driver);
    }

    public LoginPage Login(String username,String password){
        action.sendKeys(this.username,username);
        LogUtils.info(username+" in the Username field");
        action.sendKeys(this.password,password);
        LogUtils.info(password+" in the Password field");
        action.click(this.loginBtn);
        LogUtils.info("Click on the Login button");
        return this;
    }

    public HomePage isLoggedIn(String URL){
        String currentURL=driver.getCurrentUrl();
        LogUtils.info("Expected URL: "+ URL +"\nCurrent Url: "+currentURL);
        Assert.assertEquals(currentURL,URL,"Invalid URL");
        return new HomePage(driver);
    }
}
//*[@data-test='login-credentials']/text()[contains(.,'standard_user')]