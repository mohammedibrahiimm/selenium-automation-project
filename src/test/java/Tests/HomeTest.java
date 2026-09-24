package Tests;

import POM.HomePage;
import POM.LoginPage;
import Utils.JsonReader;
import Utils.PropertyReader;
import Utils.TestContext;
import drivers.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HomeTest {

    private WebDriver driver;
    private JsonReader testData;

    @BeforeMethod
    public void setUp() {
        driver = WebDriverFactory.initDriver();
        driver.get(PropertyReader.getProperty("baseUrl"));
        testData = new JsonReader(PropertyReader.getProperty("jsonFile"));

        new LoginPage(driver).login(
                testData.getJsonData("$.users.valid.username"),
                testData.getJsonData("$.users.valid.password"));
    }

    @AfterMethod
    public void tearDown() {
        WebDriverFactory.tearDown();
    }

    @Test
    public void addOneItemUpdatesBadge() {
        HomePage home = new HomePage(driver);
        home.addToCart();

        Assert.assertEquals(home.getCartBadgeCount(), 1,
                "Badge should show 1 after adding one item");
    }

    @Test
    public void addAllItemsUpdatesBadge() {
        HomePage home = new HomePage(driver);

        home.captureInventoryData();
        int expectedCount = TestContext.get(TestContext.ITEM_COUNT);

        home.addAllItemsToCart();

        Assert.assertEquals(home.getCartBadgeCount(), expectedCount,
                "Badge should equal number of items added");
    }

}
