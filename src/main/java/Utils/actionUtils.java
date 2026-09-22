package Utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class actionUtils {
    private WebDriver driver;
    private waitUtils wait;
    private WebElement element;
    public actionUtils(WebDriver driver){
        this.driver = driver;
        this.wait = new waitUtils(driver);
    }

    public void click(By Locator){
        wait.fluentWait().until(d ->
                {
                    try {
                        element = findElement(Locator);
                        new Actions(d).scrollToElement(element).perform();
                        element.click();
                        LogUtils.info("Element: "+Locator+" is clicked successfully");
                        return true;
                    }
                    catch (Exception e){
                        return false;
                    }
                }
        );
    }

    public void sendKeys(By Locator, String Text){
        wait.fluentWait().until(d ->
                {
                    try {
                    element = findElement(Locator);
                    new Actions(d).scrollToElement(element).perform();
                    element.clear();
                    element.sendKeys(Text);
                    LogUtils.info("Filling element: "+Locator+" With data "+Text+" Successfully");
                    return true;
                    } catch (Exception e) {
                        return false;
                    }
                }

        );
    }

    public String getText(By locator) {
        return wait.fluentWait().until(d -> {
            try {
                WebElement element = findElement(locator);
                new Actions(d).scrollToElement(element).perform();
                String text = element.getText();
                if (!text.isEmpty()) {
                    LogUtils.info("The Data in the Element: " + locator + " is: " + text);
                    return text;
                }
                LogUtils.warn("Element: " + locator + " is empty");
                return null;
            } catch (Exception e) {
                return null;
            }
        });
    }

    public WebElement findElement(By locator) {
        return wait.fluentWait().until(d -> {
            try {
                WebElement element = d.findElement(locator);
                new Actions(d).scrollToElement(element).perform();
                LogUtils.info("Element: " + locator + " was found successfully");
                return element;
            } catch (Exception e) {
                return null;
            }
        });
    }


}
