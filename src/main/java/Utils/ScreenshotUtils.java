package Utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;


public final class ScreenshotUtils {

    public static void takeScreenshot(WebDriver driver, String name){
        try {

            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File("test-outputs/screenshots/" + name + ".png");
            FileUtils.copyFile(src, dest);
            AllureUtils.attachScreenshotsToAllure(name,dest.getPath());
        }catch (Exception e){
            LogUtils.error(e.getMessage());
        }

    }

    private static String sanitize(String name) {
        return name.replaceAll("[^a-zA-Z0-9-_]", "_");
    }
}