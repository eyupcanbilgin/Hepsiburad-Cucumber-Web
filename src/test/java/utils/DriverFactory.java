package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.edge.EdgeDriver;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * The DriverFactory class is responsible for managing WebDriver instances.
 * It ensures that each test thread has its own WebDriver instance to enable parallel execution.
 */
public class DriverFactory {

    /**
     * ThreadLocal variable to store WebDriver instances for each test thread.
     * This ensures that each thread gets its own independent WebDriver instance.
     */
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    /**
     * A concurrent map to store WebDriver instances mapped to thread IDs.
     * This helps in managing multiple browser instances in parallel execution.
     */
    private static final ConcurrentMap<Long, WebDriver> driverMap = new ConcurrentHashMap<>();

    /**
     * Retrieves the WebDriver instance assigned to the current thread.
     *
     * @return The WebDriver instance for the current thread.
     */
    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    /**
     * Initializes and assigns a WebDriver instance for the current thread.
     * If the WebDriver is already initialized, it does nothing.
     *
     * @param browser The browser type specified (e.g., "chrome", "firefox", "edge").
     */
    public static void setDriver(String browser) {
        if (driverThreadLocal.get() == null) {
            WebDriver webDriver = createWebDriver(browser);
            driverThreadLocal.set(webDriver);
            driverMap.put(Thread.currentThread().getId(), webDriver);
        }
    }

    /**
     * Creates a WebDriver instance based on the specified browser type.
     * It supports Chrome, Firefox, and Edge browsers.
     *
     * @param browser The browser type specified (case insensitive).
     * @return A configured WebDriver instance.
     */
    private static WebDriver createWebDriver(String browser) {
        WebDriver webDriver;

        switch (browser.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                webDriver = new FirefoxDriver(BrowserOptions.firefoxOptions());
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                webDriver = new EdgeDriver();
                break;

            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                webDriver = new ChromeDriver(BrowserOptions.chromeOptions());
                break;
        }

        // Maximize the browser window and set implicit wait
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        return webDriver;
    }

    /**
     * Closes and removes the WebDriver instance assigned to the current thread.
     * Ensures proper cleanup after test execution.
     */
    public static void quitDriver() {
        Long threadId = Thread.currentThread().getId();
        WebDriver webDriver = driverMap.get(threadId);
        if (webDriver != null) {
            webDriver.quit();
            driverThreadLocal.remove();
            driverMap.remove(threadId);
        }
    }
}
