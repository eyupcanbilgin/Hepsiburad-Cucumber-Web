package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.edge.EdgeDriver;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DriverFactory {

    // Her thread'in kendine özel WebDriver nesnesi olacak
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    // Tarayıcı ismine göre ThreadLocal WebDriver oluşturma
    private static final ConcurrentMap<Long, WebDriver> driverMap = new ConcurrentHashMap<>();

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public static void setDriver(String browser) {
        if (driverThreadLocal.get() == null) {
            WebDriver webDriver = createWebDriver(browser);
            driverThreadLocal.set(webDriver);
            driverMap.put(Thread.currentThread().getId(), webDriver);
        }
    }

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

        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        return webDriver;
    }

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
