package POM;

import Utils.LogUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BaseProductPage {

    protected static final By ITEM_NAMES  = By.cssSelector("[data-test='inventory-item-name']");
    protected static final By ITEM_PRICES = By.cssSelector("[data-test='inventory-item-price']");

    protected final WebDriver driver;

    protected BaseProductPage(WebDriver driver) {
        this.driver = driver;
    }

    public List<String> getItemNames() {
        List<String> names = new ArrayList<>();
        for (WebElement el : driver.findElements(ITEM_NAMES)) {
            names.add(el.getText());
        }
        LogUtils.info("Item names: " + names);
        return names;
    }

    public List<Double> getItemPrices() {
        List<Double> prices = new ArrayList<>();
        for (WebElement el : driver.findElements(ITEM_PRICES)) {
            prices.add(Double.parseDouble(el.getText().replace("$", "").trim()));
        }
        LogUtils.info("Item prices: " + prices);
        return prices;
    }

    public int getItemCount() {
        return driver.findElements(ITEM_NAMES).size();
    }

    public double getTotalItemPrice() {
        return getItemPrices().stream()
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    public List<Integer> getItemQuantities() {
        return Collections.nCopies(getItemCount(), 1);
    }
}