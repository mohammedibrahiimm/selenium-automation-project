package POM;

import Utils.ActionUtils;
import Utils.LogUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends BaseProductPage {

    private static final By CART_ITEMS              = By.cssSelector("[data-test='inventory-item']");
    private static final By ITEM_QUANTITIES         = By.cssSelector("[data-test='item-quantity']");
    private static final By REMOVE_BUTTONS          = By.cssSelector("button[data-test^='remove']");
    private static final By CONTINUE_SHOPPING_BTN   = By.cssSelector("[data-test='continue-shopping']");
    private static final By CHECKOUT_BUTTON         = By.cssSelector("[data-test='checkout']");

    private final ActionUtils action;

    public CartPage(WebDriver driver) {
        super(driver);
        this.action = new ActionUtils(driver);
    }

    // ---------- Read operations ----------

    @Override
    public List<Integer> getItemQuantities() {
        List<Integer> quantities = new ArrayList<>();
        for (WebElement el : driver.findElements(ITEM_QUANTITIES)) {
            quantities.add(Integer.parseInt(el.getText().trim()));
        }
        return quantities;
    }

    public boolean isCartEmpty() {
        return driver.findElements(CART_ITEMS).isEmpty();
    }

    // ---------- Action operations ----------

    /** Removes a single item by its visible name. Throws if not found. */
    public CartPage removeItemByName(String itemName) {
        for (WebElement cartItem : driver.findElements(CART_ITEMS)) {
            WebElement nameEl = cartItem.findElement(
                    By.cssSelector("[data-test='inventory-item-name']"));
            if (nameEl.getText().equals(itemName)) {
                cartItem.findElement(
                        By.cssSelector("button[data-test^='remove']")).click();
                LogUtils.info("Removed from cart: " + itemName);
                return this;
            }
        }
        throw new NoSuchElementException("Item not found in cart: " + itemName);
    }

    /** Removes every item from the cart. Loops until empty. */
    public CartPage removeAllItems() {
        int guard = 100;   // safety against infinite loop
        while (isCartEmpty() == false && guard-- > 0) {
            driver.findElements(REMOVE_BUTTONS).get(0).click();
        }
        LogUtils.info("Cart emptied");
        return this;
    }

    /** Clicks "Continue Shopping" — returns to inventory page. */
    public HomePage clickContinueShopping() {
        action.click(CONTINUE_SHOPPING_BTN);
        return new HomePage(driver);
    }

    /** Clicks "Checkout" — returns the checkout page. */
    public CheckoutPage clickCheckout() {
        action.click(CHECKOUT_BUTTON);
        return new CheckoutPage(driver);
    }
}