package drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ThreadGuard;

public class WebDriverFactory {

    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private static AbstractDriver getDrive(String browser){
        return switch (browser){
            case "chrome" -> new ChromeFactory();
            case "edge" -> new EdgeFactory();
            default -> throw new IllegalArgumentException("Browser not supported");
        };
    }

    public static WebDriver initDriver(String browser){
        WebDriver driver = ThreadGuard.protect(getDrive(browser).createDriver());
        driverThreadLocal.set(driver);
        return driverThreadLocal.get();
    }

    public static void tearDown(){
        driverThreadLocal.get().quit();
        driverThreadLocal.remove();
    }
}
