package POM;

import Utils.LogUtils;
import Utils.TestContext;
import Utils.ActionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class HomePage {

    private static final By ADD_TO_CART_BUTTONS = By.xpath("//button[text()='Add to cart']");
    private static final By CART_BADGE          = By.className("shopping_cart_badge");
    private static final By ITEM_PRICES         = By.xpath("//*[@data-test='inventory-item-price']");
    private static final By SAUCE_LABS_BACKPACK = By.id("add-to-cart-sauce-labs-backpack");
    private static final By CART_LINK           = By.className("shopping_cart_link");
    private static final By ITEM_NAMES          = By.className("inventory_item_name");

    private final WebDriver driver;
    private final ActionUtils action;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.action = new ActionUtils(driver);
    }

    public HomePage addToCart() {
        action.click(SAUCE_LABS_BACKPACK);
        return this;
    }

    public int getCartBadgeCount() {
        return Integer.parseInt(action.getText(CART_BADGE).trim());
    }

    /** Reads inventory, stores data in TestContext, adds all items to cart. */
    public double getTotalItemPrice() {
        List<WebElement> priceElements = driver.findElements(ITEM_PRICES);
        List<WebElement> nameElements  = driver.findElements(ITEM_NAMES);

        double sum = 0.0;
        List<String> names = new ArrayList<>();

        for (WebElement el : priceElements) {
            sum += Double.parseDouble(el.getText().replace("$", "").trim());
        }
        for (WebElement el : nameElements) {
            names.add(el.getText());
        }

        TestContext.set(TestContext.TOTAL_PRICE, sum);
        TestContext.set(TestContext.ITEM_COUNT, priceElements.size());
        TestContext.set(TestContext.ITEM_NAMES, names);

        // Now add every item to cart
        List<WebElement> buttons = driver.findElements(ADD_TO_CART_BUTTONS);
        for (WebElement btn : buttons) {
            btn.click();
        }
        LogUtils.info("Added " + buttons.size() + " items, total = " + sum);
        return sum;
    }

    public CartPage navigateToCart() {
        action.click(CART_LINK);
        return new CartPage(driver);
    }
}