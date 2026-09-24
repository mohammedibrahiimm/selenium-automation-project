package Tests;

import POM.CartPage;
import POM.CheckoutOverviewPage;
import POM.CheckoutPage;
import POM.ConfirmationPage;
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

public class CheckoutTest {

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

    private CheckoutOverviewPage goToCheckoutOverview() {
        CartPage cart = new HomePage(driver).addAllItemsToCart().navigateToCart();
        CheckoutPage form = cart.clickCheckout();

        return form.fillInfo(
                testData.getJsonData("$.checkout.firstName"),
                testData.getJsonData("$.checkout.lastName"),
                testData.getJsonData("$.checkout.zipCode")
        ).clickContinue();
    }

    private CheckoutPage goToCheckoutForm() {
        return new HomePage(driver)
                .addAllItemsToCart()
                .navigateToCart()
                .clickCheckout();
    }

    @Test
    public void completeCheckoutFlow() {
        HomePage home = new HomePage(driver);
        List<String> inventoryNames = home.getItemNames();
        List<Double> inventoryPrices = home.getItemPrices();
        double inventoryTotal = home.getTotalItemPrice();

        CheckoutOverviewPage overview = home
                .addAllItemsToCart()
                .navigateToCart()
                .clickCheckout()
                .fillInfo(
                        testData.getJsonData("$.checkout.firstName"),
                        testData.getJsonData("$.checkout.lastName"),
                        testData.getJsonData("$.checkout.zipCode"))
                .clickContinue();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(overview.getItemNames(), inventoryNames,
                "Item names should persist to checkout overview");
        softAssert.assertEquals(overview.getItemPrices(), inventoryPrices,
                "Item prices should persist to checkout overview");

        double subtotal = overview.getSubtotal();
        double tax = overview.getTax();
        double total = overview.getTotal();

        softAssert.assertEquals(subtotal, inventoryTotal, 0.01,
                "Subtotal should equal inventory total");
        softAssert.assertEquals(total, subtotal + tax, 0.01,
                "Total should equal subtotal + tax");
        softAssert.assertAll();

        ConfirmationPage confirmation = overview.clickFinish();
        Assert.assertTrue(confirmation.isOrderComplete(),
                "Order confirmation page should show success message");
    }

    @Test
    public void checkoutOverviewMatchesCart() {
        CheckoutOverviewPage overview = goToCheckoutOverview();

        Assert.assertEquals(overview.getItemCount(), 6,
                "Overview should show all 6 items");
    }

    @Test
    public void checkoutWithEmptyFormShowsFirstNameError() {
        CheckoutPage form = goToCheckoutForm();

        form.clickContinue();

        Assert.assertEquals(form.getErrorMessage(),
                testData.getJsonData("$.errorMessages.checkoutFirstNameRequired"),
                "Should show first name required error");
    }

    @Test
    public void checkoutWithOnlyFirstNameShowsLastNameError() {
        CheckoutPage form = goToCheckoutForm();

        form.fillInfo(
                        testData.getJsonData("$.checkout.firstName"),
                        "", "")
                .clickContinue();

        Assert.assertEquals(form.getErrorMessage(),
                testData.getJsonData("$.errorMessages.checkoutLastNameRequired"),
                "Should show last name required error");
    }

    @Test
    public void checkoutWithMissingPostalCodeShowsPostalError() {
        CheckoutPage form = goToCheckoutForm();

        form.fillInfo(
                        testData.getJsonData("$.checkout.firstName"),
                        testData.getJsonData("$.checkout.lastName"),
                        "")
                .clickContinue();

        Assert.assertEquals(form.getErrorMessage(),
                testData.getJsonData("$.errorMessages.checkoutPostalCodeRequired"),
                "Should show postal code required error");
    }
}