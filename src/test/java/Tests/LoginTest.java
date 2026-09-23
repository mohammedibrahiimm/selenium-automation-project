package Tests;

import POM.LoginPage;
import drivers.WebDriverFactory;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest {
    WebDriver driver;

    @Test
    public void validLoginTest(){
        new LoginPage(driver).
                Login("standard_user","secret_sauce").
                isLoggedIn("https://www.saucedemo.com/inventory.html");
    }

    @Test
    public void invalidLoginTest(){
        new LoginPage(driver).
        Login("locked_out_user","secret_sauce").
                isLoggedIn("https://www.saucedemo.com/");
    }

    @BeforeMethod
    public void setUp(){
        driver = WebDriverFactory.initDriver("edge");
        driver.get("https://www.saucedemo.com/");
    }
    @AfterMethod
    public void tearDown(){
        WebDriverFactory.tearDown();
    }
}
