package POM;

import Utils.ActionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ConfirmationPage {

    private static final By COMPLETE_HEADER = By.className("complete-header");
    private static final By BACK_HOME_BTN   = By.id("back-to-products");

    private final WebDriver driver;
    private final ActionUtils action;

    public ConfirmationPage(WebDriver driver) {
        this.driver = driver;
        this.action = new ActionUtils(driver);
    }

    public String getConfirmationMessage() {
        return action.getText(COMPLETE_HEADER);
    }

    public boolean isOrderComplete() {
        return getConfirmationMessage().toLowerCase().contains("thank you");
    }

    public HomePage clickBackHome() {
        action.click(BACK_HOME_BTN);
        return new HomePage(driver);
    }
}