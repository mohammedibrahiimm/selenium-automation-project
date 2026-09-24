package Tests;

import POM.CartPage;
import POM.HomePage;
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

import java.util.List;

public class CartTest {

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

    /** Helper — adds every item and lands on the cart page. */
    private CartPage goToCartWithAllItems() {
        return new HomePage(driver).addAllItemsToCart().navigateToCart();
    }

    @Test
    public void cartMatchesInventory() {
        HomePage home = new HomePage(driver);

        List<String> inventoryNames  = home.getItemNames();
        List<Double> inventoryPrices = home.getItemPrices();
        int inventoryCount           = home.getItemCount();

        CartPage cart = home.addAllItemsToCart().navigateToCart();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(cart.getItemCount(), inventoryCount,
                "Cart count should match inventory");
        softAssert.assertEquals(cart.getItemNames(), inventoryNames,
                "Cart names should match inventory");
        softAssert.assertEquals(cart.getItemPrices(), inventoryPrices,
                "Cart prices should match inventory");
        softAssert.assertAll();
    }

    @Test
    public void removeSingleItemUpdatesCart() {
        CartPage cart = goToCartWithAllItems();

        int initialCount       = cart.getItemCount();
        double initialTotal    = cart.getTotalItemPrice();
        String itemToRemove    = cart.getItemNames().get(0);
        double removedPrice    = cart.getItemPrices().get(0);

        cart.removeItemByName(itemToRemove);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(cart.getItemCount(), initialCount - 1,
                "Count should decrease by one after removal");
        softAssert.assertFalse(cart.getItemNames().contains(itemToRemove),
                "Removed item should no longer be in cart");
        softAssert.assertEquals(cart.getTotalItemPrice(),
                initialTotal - removedPrice, 0.01,
                "Total should drop by the removed item's price");
        softAssert.assertAll();
    }

    @Test
    public void removeAllItemsEmptiesCart() {
        CartPage cart = goToCartWithAllItems();

        cart.removeAllItems();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(cart.isCartEmpty(),
                "Cart should be empty after removing all items");
        softAssert.assertEquals(cart.getItemCount(), 0,
                "Item count should be 0");
        softAssert.assertAll();
    }

    @Test
    public void continueShoppingReturnsToInventory() {
        CartPage cart = goToCartWithAllItems();

        cart.clickContinueShopping();

        Assert.assertEquals(driver.getCurrentUrl(),
                PropertyReader.getProperty("homePageUrl"),
                "Continue Shopping should return to inventory page");
    }

    @Test
    public void checkoutNavigatesToCheckoutPage() {
        CartPage cart = goToCartWithAllItems();

        cart.clickCheckout();

        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one"),
                "Checkout should navigate to checkout step one");
    }
}