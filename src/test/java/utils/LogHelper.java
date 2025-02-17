package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogHelper {
    // Genel LogHelper sınıfı için bir Logger oluşturuyoruz.
    private static final Logger logger = LoggerFactory.getLogger(LogHelper.class);

    public static void info(String message) {
        logger.info(message);
    }

    public static void debug(String message) {
        logger.debug(message);
    }

    public static void warn(String message) {
        logger.warn(message);
    }

    public static void error(String message) {
        logger.error(message);
    }

    public static void error(String message, Throwable t) {
        logger.error(message, t);
    }
}
