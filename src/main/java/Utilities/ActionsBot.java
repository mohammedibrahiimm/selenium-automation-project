package Utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;

public class ActionsBot {

    private static final String SCREENSHOT_PATH = "test-outputs/Screenshots/";
    private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd-h-m-ssa";

    private final WebDriver driver;
    private final Wait waitBot;

    public ActionsBot(WebDriver driver) {
        this.driver = driver;
        this.waitBot = new Wait(driver);
    }

    public void clickOnElement(By locator) {
        withRetry(locator, element -> {
            scrollToElement(element);
            element.click();
        });
    }

    public void type(By locator, String text) {
        withRetry(locator, element -> {
            scrollToElement(element);
            element.clear();
            element.sendKeys(text);
        });
    }

    public String getText(By locator) {
        return waitBot.fluentWait().until(d -> {
            try {
                WebElement element = findWebElement(locator);
                scrollToElement(element);
                return element.getText();
            } catch (Exception e) {
                return null;
            }
        });
    }

    public void handleDropdownByValue(By locator, String value) {
        withRetry(locator, element -> new Select(element).selectByValue(value));
    }

    public void handleDropdownByVisibleText(By locator, String text) {
        withRetry(locator, element -> new Select(element).selectByVisibleText(text));
    }

    public void handleDropdownByIndex(By locator, int index) {
        withRetry(locator, element -> new Select(element).selectByIndex(index));
    }

    public String handleDropdownGetFirstSelectedOption(By locator) {
        return waitBot.fluentWait().until(d -> {
            try {
                WebElement element = findWebElement(locator);
                scrollToElement(element);
                return new Select(element).getFirstSelectedOption().getText();
            } catch (Exception e) {
                return null;
            }
        });
    }

    public WebElement findWebElement(By locator) {
        return driver.findElement(locator);
    }

    public void scrollToElement(By locator) {
        scrollToElement(findWebElement(locator));
    }

    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    public void takeScreenshot(String imageName){
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File(SCREENSHOT_PATH + imageName + "-" + getTimestamp() + ".png");
            FileUtils.forceMkdirParent(dest);
            FileUtils.copyFile(src, dest);
            AllureUtils.attachScreenshotsToAllure(imageName, dest.getPath());
        } catch (Exception e) {
            LogsUtils.warn("Failed to capture screenshot '{}'", imageName, e.getMessage());
        }
    }

    public static String getTimestamp() {
        return new SimpleDateFormat(TIMESTAMP_FORMAT).format(new Date());
    }

    /**
     * Runs the given action against the element behind {@code locator}, retrying
     * until it succeeds or the wait times out.
     */
    private void withRetry(By locator, Consumer<WebElement> action) {
        waitBot.fluentWait().until(d -> {
            try {
                action.accept(findWebElement(locator));
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }
}