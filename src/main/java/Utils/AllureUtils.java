package Utils;

import com.google.common.collect.ImmutableMap;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;
import static java.nio.file.Files.newInputStream;

public class AllureUtils {

    public static void cleanAllureResults() {
        FileUtils.deleteQuietly(new File("test-outputs/allure-results"));
    }

    public static void attachScreenshotsToAllure(String screenName, String screenPath) {
        try {
            Allure.attachment(screenName, newInputStream(Path.of(screenPath)));
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
        }
    }

    public static void setAllureEnvironment() {
        try {
            Path allureResultsPath = Path.of(
                    System.getProperty("user.dir"),
                    "test-outputs",
                    "allure-results"
            );
            Files.createDirectories(allureResultsPath);

            allureEnvironmentWriter(
                    ImmutableMap.<String, String>builder()
                            .put("OS", System.getProperty("os.name"))
                            .put("JDK Version", System.getProperty("java.runtime.version"))
                            .put("URL", PropertyReader.getProperty("baseUrl"))
                            .build(),
                    allureResultsPath.toString() + File.separator
            );
        } catch (IOException e) {
            LogUtils.error("Failed to write Allure environment: " + e.getMessage());
        }
    }
}