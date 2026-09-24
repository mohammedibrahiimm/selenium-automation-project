package Utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class ActionUtils {

    private final WebDriver driver;
    private final WaitUtils wait;

    public ActionUtils(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
    }

    public void click(By locator) {
        wait.fluentWait().until(d -> {
            try {
                WebElement el = findElement(locator);
                new Actions(d).scrollToElement(el).perform();
                el.click();
                LogUtils.info("Clicked: " + locator);
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }

    public void sendKeys(By locator, String text) {
        wait.fluentWait().until(d -> {
            try {
                WebElement el = findElement(locator);
                new Actions(d).scrollToElement(el).perform();
                el.clear();
                el.sendKeys(text);
                LogUtils.info("Typed into: " + locator);
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }

    public String getText(By locator) {
        return wait.fluentWait().until(d -> {
            try {
                WebElement el = findElement(locator);
                String text = el.getText();
                return (text != null && !text.isEmpty()) ? text : null;
            } catch (Exception e) {
                return null;
            }
        });
    }

    /** Raw find. The caller's fluent wait handles retry — do not nest waits. */
    public WebElement findElement(By locator) {
        return driver.findElement(locator);
    }
}