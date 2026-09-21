package Utilities;

import com.google.common.collect.ImmutableMap;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;

public final class AllureUtils {

    private static final Logger log = LoggerFactory.getLogger(AllureUtils.class);

    private static final Path ALLURE_RESULTS_DIR = Path.of("test-output", "allure-results");
    private static final String APP_URL = "https://www.saucedemo.com/v1/index.html";

    private AllureUtils() {
        // utility class
    }

    public static void cleanAllureResults() {
        FileUtils.deleteQuietly(ALLURE_RESULTS_DIR.toFile());
    }

    public static void attachScreenshotsToAllure(String screenName, String screenPath) {
        Path path = Path.of(screenPath);
        try {
            // Allure 3 API: read the file content as a byte array
            byte[] content = Files.readAllBytes(path);
            Allure.attachment(screenName, Arrays.toString(content));
        } catch (IOException e) {
            throw new UncheckedIOException(
                    "Failed to attach screenshot '" + screenName + "' from " + screenPath, e);
        }
    }

    public static void setAllureEnvironment() {
        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("OS", PropertyReader.getProperty("os.name"))
                        .put("JDK Version", PropertyReader.getProperty("java.runtime.version"))
                        .put("URL", APP_URL)
                        .build(),
                ALLURE_RESULTS_DIR.toAbsolutePath() + File.separator
        );
    }
}