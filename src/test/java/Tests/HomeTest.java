package Tests;

import POM.HomePage;
import POM.LoginPage;
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

    @BeforeMethod
    public void setUp() {
        driver = WebDriverFactory.initDriver();
        driver.get(PropertyReader.getProperty("baseUrl"));
        new LoginPage(driver).login(
                PropertyReader.getProperty("validUsername"),
                PropertyReader.getProperty("validPassword"));
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
        home.getTotalItemPrice();   // captures prices/count/names AND adds all items to cart

        int expected = TestContext.get(TestContext.ITEM_COUNT);
        Assert.assertEquals(home.getCartBadgeCount(), expected,
                "Badge should equal number of items added");
    }
}