package Utils;

import drivers.WebDriverFactory;
import io.qameta.allure.Allure;
import io.qameta.allure.AttachmentOptions;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ScreenshotUtils {

    private static final String SCREENSHOT_DIR = "test-outputs/screenshots/";
    private static final DateTimeFormatter TIMESTAMP =
            DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    private ScreenshotUtils() {}

    public static String capture(String testName) {
        WebDriver driver;
        try {
            driver = WebDriverFactory.getDriver();
        } catch (IllegalStateException e) {
            LogUtils.warn("Skipping screenshot — driver not available: " + e.getMessage());
            return null;
        }

        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

            String fileName = sanitize(testName) + "_"
                    + LocalDateTime.now().format(TIMESTAMP) + ".png";
            Path filePath = Paths.get(SCREENSHOT_DIR, fileName);

            Files.createDirectories(filePath.getParent());
            Files.write(filePath, screenshot);

            // Allure 3.x API: attachment(name, type, InputStream, options)
            Allure.attachment(
                    "Screenshot — " + testName,
                    "image/png",
                    new ByteArrayInputStream(screenshot),
                    AttachmentOptions.empty()
            );

            LogUtils.info("Screenshot saved: " + filePath);
            return filePath.toString();

        } catch (Exception e) {
            LogUtils.warn("Screenshot capture failed for " + testName + ": " + e.getMessage());
            return null;
        }
    }

    private static String sanitize(String name) {
        return name.replaceAll("[^a-zA-Z0-9-_]", "_");
    }
}