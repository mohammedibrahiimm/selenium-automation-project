package Tests;

import POM.LoginPage;
import Utils.PropertyReader;
import drivers.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest {

    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = WebDriverFactory.initDriver();
        driver.get(PropertyReader.getProperty("baseUrl"));
    }

    @AfterMethod
    public void tearDown() {
        WebDriverFactory.tearDown();
    }

    @Test
    public void validLoginTest() {
        new LoginPage(driver).login(
                PropertyReader.getProperty("validUsername"),
                PropertyReader.getProperty("validPassword"));

        Assert.assertEquals(driver.getCurrentUrl(),
                PropertyReader.getProperty("homePageUrl"),
                "Should land on home page after valid login");
    }

    @Test
    public void invalidLoginTest() {
        new LoginPage(driver).login(
                PropertyReader.getProperty("invalidUsername"),
                PropertyReader.getProperty("invalidPassword"));

        Assert.assertEquals(driver.getCurrentUrl(),
                PropertyReader.getProperty("baseUrl"),
                "Should stay on login page after invalid login");
    }
}