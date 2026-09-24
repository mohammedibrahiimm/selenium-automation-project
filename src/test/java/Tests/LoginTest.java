package Tests;

import POM.LoginPage;
import Utils.JsonReader;
import Utils.PropertyReader;
import drivers.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class LoginTest {

    private WebDriver driver;
    private JsonReader testData;

    @BeforeMethod
    public void setUp() {
        driver = WebDriverFactory.initDriver();
        driver.get(PropertyReader.getProperty("baseUrl"));
        testData = new JsonReader(PropertyReader.getProperty("jsonFile"));
    }

    @AfterMethod
    public void tearDown() {
        WebDriverFactory.tearDown();
    }

    @Test
    public void validLogin() {
        new LoginPage(driver).login(
                testData.getJsonData("$.users.valid.username"),
                testData.getJsonData("$.users.valid.password"));

        Assert.assertEquals(driver.getCurrentUrl(),
                PropertyReader.getProperty("homePageUrl"),
                "Should land on home page after valid login");
    }

    @Test
    public void lockedOutUserLogin() {
        new LoginPage(driver).login(
                testData.getJsonData("$.users.invalid.username"),
                testData.getJsonData("$.users.invalid.password"));

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(driver.getCurrentUrl(),
                PropertyReader.getProperty("baseUrl"),
                "Should stay on login page for locked-out user");
        softAssert.assertEquals(new LoginPage(driver).getErrorMessage(),
                testData.getJsonData("$.errorMessages.lockedOut"),
                "Should show locked-out error");
        softAssert.assertAll();
    }

    @Test
    public void wrongPasswordLogin() {
        new LoginPage(driver).login(
                testData.getJsonData("$.users.valid.username"),
                testData.getJsonData("$.users.invalid.password1"));

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(driver.getCurrentUrl(),
                PropertyReader.getProperty("baseUrl"),
                "Should stay on login page for wrong password");
        softAssert.assertEquals(new LoginPage(driver).getErrorMessage(),
                testData.getJsonData("$.errorMessages.invalidData"),
                "Should show invalid credentials error");
        softAssert.assertAll();
    }

    @Test
    public void loginWithEmptyPassword() {
        new LoginPage(driver).loginWithEmptyPassword(
                testData.getJsonData("$.users.valid.username"));

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(driver.getCurrentUrl(),
                PropertyReader.getProperty("baseUrl"),
                "Should stay on login page with empty password");
        softAssert.assertEquals(new LoginPage(driver).getErrorMessage(),
                testData.getJsonData("$.errorMessages.emptyPasswordField"),
                "Should show empty password error");
        softAssert.assertAll();
    }

    @Test
    public void loginWithEmptyUsername() {
        new LoginPage(driver).loginWithEmptyUsername(
                testData.getJsonData("$.users.valid.password"));

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(driver.getCurrentUrl(),
                PropertyReader.getProperty("baseUrl"),
                "Should stay on login page with empty username");
        softAssert.assertEquals(new LoginPage(driver).getErrorMessage(),
                testData.getJsonData("$.errorMessages.emptyUsernameField"),
                "Should show empty username error");
        softAssert.assertAll();
    }
}