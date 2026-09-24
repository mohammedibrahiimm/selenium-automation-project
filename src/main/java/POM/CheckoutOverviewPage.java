package POM;

import Utils.ActionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutOverviewPage extends BaseProductPage {

    private static final By SUBTOTAL_LABEL = By.cssSelector("[data-test='subtotal-label']");
    private static final By TAX_LABEL      = By.cssSelector("[data-test='tax-label']");
    private static final By TOTAL_LABEL    = By.cssSelector("[data-test='total-label']");
    private static final By FINISH_BUTTON  = By.id("finish");
    private static final By CANCEL_BUTTON  = By.id("cancel");

    private final ActionUtils action;

    public CheckoutOverviewPage(WebDriver driver) {
        super(driver);
        this.action = new ActionUtils(driver);
    }

    public double getSubtotal() {
        return parseMoney(action.getText(SUBTOTAL_LABEL));
    }

    public double getTax() {
        return parseMoney(action.getText(TAX_LABEL));
    }

    public double getTotal() {
        return parseMoney(action.getText(TOTAL_LABEL));
    }

    public ConfirmationPage clickFinish() {
        action.click(FINISH_BUTTON);
        return new ConfirmationPage(driver);
    }

    public HomePage clickCancel() {
        action.click(CANCEL_BUTTON);
        return new HomePage(driver);
    }

    /** Extracts the number from strings like "Item total: $129.94". */
    private double parseMoney(String text) {
        if (text == null || text.isBlank()) {
            throw new IllegalStateException("Price text was empty");
        }
        return Double.parseDouble(text.replaceAll("[^0-9.]", ""));
    }
}