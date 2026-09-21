package Utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class LogsUtils {

    private static final StackWalker WALKER =
            StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    private LogsUtils() {}

    public static void info(String... msg) { logger().info(msg); }
    public static void error(String... msg) { logger().error(msg); }
    public static void fatal(String... msg) { logger().fatal(msg); }
    public static void warn(String... msg) { logger().warn(msg); }
    public static void debug(String... msg) { logger().debug(msg); }
    public static void trace(String... msg) { logger().trace(msg); }

    private static Logger logger() {
        return LogManager.getLogger(WALKER.getCallerClass());
    }
}