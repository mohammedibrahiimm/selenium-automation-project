package Utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;
import java.util.Properties;

public class PropertyReader {

    private static final Properties PROPERTIES = new Properties();
    private static boolean loaded = false;

    private PropertyReader() {}

    public static synchronized void loadProperties() {
        if (loaded) return;
        try {
            Collection<File> files = FileUtils.listFiles(
                    new File("src/main/resources"),
                    new String[]{"properties"},
                    true);
            for (File file : files) {
                try (InputStream in = FileUtils.openInputStream(file)) {
                    PROPERTIES.load(in);
                }
            }
            // System props win — useful for -Dbrowser=firefox in CI
            PROPERTIES.putAll(System.getProperties());
            loaded = true;
            LogUtils.info("Loaded " + PROPERTIES.size() + " properties");
        } catch (Exception e) {
            throw new RuntimeException("Failed to load properties", e);
        }
    }

    public static String getProperty(String key) {
        if (!loaded) loadProperties();
        String value = PROPERTIES.getProperty(key);
        if (value == null) LogUtils.warn("Property not found: " + key);
        return value;
    }
}