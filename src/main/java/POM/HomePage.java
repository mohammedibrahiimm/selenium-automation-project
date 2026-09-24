package POM;

import Utils.ActionUtils;
import Utils.LogUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HomePage extends BaseProductPage {

    private static final By ADD_TO_CART_BUTTONS = By.xpath("//button[text()='Add to cart']");
    private static final By CART_BADGE          = By.className("shopping_cart_badge");
    private static final By SAUCE_LABS_BACKPACK = By.id("add-to-cart-sauce-labs-backpack");
    private static final By CART_LINK           = By.className("shopping_cart_link");

    private final ActionUtils action;

    public HomePage(WebDriver driver) {
        super(driver);
        this.action = new ActionUtils(driver);
    }

    public HomePage addToCart() {
        action.click(SAUCE_LABS_BACKPACK);
        return this;
    }

    public int getCartBadgeCount() {
        return Integer.parseInt(action.getText(CART_BADGE).trim());
    }

    public HomePage addAllItemsToCart() {
        List<WebElement> buttons = driver.findElements(ADD_TO_CART_BUTTONS);
        for (WebElement btn : buttons) {
            btn.click();
        }
        LogUtils.info("Added " + buttons.size() + " items to cart");
        return this;
    }

    public CartPage navigateToCart() {
        action.click(CART_LINK);
        return new CartPage(driver);
    }
}