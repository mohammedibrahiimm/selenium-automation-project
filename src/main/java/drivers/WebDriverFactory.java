package drivers;

import Utils.CleanupUtils;
import Utils.LogUtils;
import Utils.PropertyReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ThreadGuard;

public class WebDriverFactory {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private WebDriverFactory() {}

    private static WebDriver createDriver() {
        String browserName = PropertyReader.getProperty("browser");
        if (browserName == null || browserName.isBlank()) {
            throw new IllegalStateException("Property 'browser' is not set in config");
        }
        Browser browserType = Browser.valueOf(browserName.trim().toUpperCase());
        LogUtils.info("Browser type: " + browserType);
        return browserType.getDriverFactory().createDriver();
    }

    public static WebDriver initDriver() {
        WebDriver driver = ThreadGuard.protect(createDriver());
        DRIVER.set(driver);
        LogUtils.info("Driver initialized");
        return driver;
    }

    public static WebDriver getDriver() {
        WebDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException(
                    "Driver not initialized — call WebDriverFactory.initDriver() first");
        }
        return driver;
    }

    public static void tearDown() {
        WebDriver driver = DRIVER.get();
        if (driver == null) {
            LogUtils.warn("tearDown called but no driver in ThreadLocal — skipping");
            return;
        }

        CleanupUtils.cleanBrowserState(driver);

        try {
            driver.quit();
            LogUtils.info("Driver quit");
        } catch (Exception e) {
            LogUtils.error("Error quitting driver: " + e.getMessage());
        } finally {
            DRIVER.remove();
            LogUtils.info("Driver removed from ThreadLocal");
        }
    }
}