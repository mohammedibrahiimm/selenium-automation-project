package Utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtils {

    private LogUtils() {}

    private static Logger logger() {
        for (StackTraceElement el : Thread.currentThread().getStackTrace()) {
            String name = el.getClassName();
            if (!name.equals(LogUtils.class.getName())
                    && !name.equals(Thread.class.getName())) {
                return LogManager.getLogger(name);
            }
        }
        return LogManager.getLogger(LogUtils.class);
    }

    public static void info(String... message) {
        logger().info(String.join(" ", message));
    }

    public static void error(String... message) {
        logger().error(String.join(" ", message));
    }

    public static void warn(String... message) {
        logger().warn(String.join(" ", message));
    }

    public static void fatal(String... message) {
        logger().fatal(String.join(" ", message));
    }

    public static void debug(String... message) {
        logger().debug(String.join(" ", message));
    }
}