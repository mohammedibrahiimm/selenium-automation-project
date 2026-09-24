package POM;

import Utils.ActionUtils;
import Utils.LogUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage {

    private static final By FIRST_NAME_FIELD = By.id("first-name");
    private static final By LAST_NAME_FIELD  = By.id("last-name");
    private static final By ZIP_CODE_FIELD   = By.id("postal-code");
    private static final By CONTINUE_BUTTON  = By.id("continue");
    private static final By CANCEL_BUTTON    = By.id("cancel");
    private static final By ERROR_MESSAGE    = By.cssSelector("[data-test='error']");

    private final WebDriver driver;
    private final ActionUtils action;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.action = new ActionUtils(driver);
    }

    public CheckoutPage fillInfo(String firstName, String lastName, String zipCode) {
        action.sendKeys(FIRST_NAME_FIELD, firstName);
        action.sendKeys(LAST_NAME_FIELD, lastName);
        action.sendKeys(ZIP_CODE_FIELD, zipCode);
        LogUtils.info("Checkout form filled for " + firstName + " " + lastName);
        return this;
    }

    public CheckoutOverviewPage clickContinue() {
        action.click(CONTINUE_BUTTON);
        return new CheckoutOverviewPage(driver);
    }

    public CartPage clickCancel() {
        action.click(CANCEL_BUTTON);
        return new CartPage(driver);
    }

    public String getErrorMessage() {
        return action.getText(ERROR_MESSAGE);
    }
}