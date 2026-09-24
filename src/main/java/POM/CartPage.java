package POM;

import Utils.LogUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class CartPage {

    private static final By ITEM_NAMES  = By.className("inventory_item_name");
    private static final By ITEM_PRICES = By.xpath("//*[@data-test='inventory-item-price']");

    private final WebDriver driver;

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public double getTotalItemPrice() {
        List<WebElement> priceElements = driver.findElements(ITEM_PRICES);
        double sum = 0.0;
        for (WebElement el : priceElements) {
            sum += Double.parseDouble(el.getText().replace("$", "").trim());
        }
        LogUtils.info("Cart total: " + sum);
        return sum;
    }

    public List<String> getItemNames() {
        List<String> names = new ArrayList<>();
        for (WebElement el : driver.findElements(ITEM_NAMES)) {
            names.add(el.getText());
        }
        return names;
    }
}