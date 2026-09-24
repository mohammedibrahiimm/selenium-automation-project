package Utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public final class CleanupUtils {

    private CleanupUtils() {}

    /** Clears cookies, localStorage, and sessionStorage. Best-effort. */
    public static void cleanBrowserState(WebDriver driver) {
        if (driver == null) return;
        try {
            driver.manage().deleteAllCookies();
            if (driver instanceof JavascriptExecutor js) {
                js.executeScript("window.localStorage.clear();");
                js.executeScript("window.sessionStorage.clear();");
            }
            LogUtils.info("Browser state cleaned");
        } catch (Exception e) {
            LogUtils.warn("Browser cleanup failed: " + e.getMessage());
        }
    }

    /** Navigates to the base URL and clears storage. Useful for resetting mid-suite. */
    public static void resetToHome(WebDriver driver, String baseUrl) {
        cleanBrowserState(driver);
        driver.get(baseUrl);
        LogUtils.info("Reset to base URL: " + baseUrl);
    }
}