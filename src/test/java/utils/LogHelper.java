package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The LogHelper class provides a centralized logging utility for the test automation framework.
 * It uses SLF4J (Simple Logging Facade for Java) with Logback for structured logging.
 */
public class LogHelper {

    /**
     * Logger instance used to log messages at different levels.
     * It is configured using SLF4J with Logback as the underlying implementation.
     */
    private static final Logger logger = LoggerFactory.getLogger(LogHelper.class);

    /**
     * Logs an informational message.
     *
     * @param message The message to be logged.
     */
    public static void info(String message) {
        logger.info("[INFO] " + message);
    }

    /**
     * Logs a debug message, useful for troubleshooting.
     *
     * @param message The message to be logged.
     */
    public static void debug(String message) {
        logger.debug("[DEBUG] " + message);
    }

    /**
     * Logs a warning message, typically used for non-critical issues.
     *
     * @param message The warning message to be logged.
     */
    public static void warn(String message) {
        logger.warn("[WARN] " + message);
    }

    /**
     * Logs an error message, used for handling failures.
     *
     * @param message The error message to be logged.
     */
    public static void error(String message) {
        logger.error("[ERROR] " + message);
    }

    /**
     * Logs an error message along with an exception stack trace.
     * Useful for debugging exceptions in the test execution process.
     *
     * @param message The error message to be logged.
     * @param t The throwable exception to be included in the log.
     */
    public static void error(String message, Throwable t) {
        logger.error("[ERROR] " + message, t);
    }
}
