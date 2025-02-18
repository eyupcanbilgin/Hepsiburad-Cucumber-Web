package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogHelper {
    private static final Logger logger = LoggerFactory.getLogger(LogHelper.class);

    public static void info(String message) {
        logger.info("[INFO] " + message);
    }

    public static void debug(String message) {
        logger.debug("[DEBUG] " + message);
    }

    public static void warn(String message) {
        logger.warn("[WARN] " + message);
    }

    public static void error(String message) {
        logger.error("[ERROR] " + message);
    }

    public static void error(String message, Throwable t) {
        logger.error("[ERROR] " + message, t);
    }
}
