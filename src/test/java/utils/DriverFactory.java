package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class DriverFactory {
    // Her thread için ayrı WebDriver örneği tutmak üzere ThreadLocal kullanıyoruz.
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    // Driver'a erişim sağlamak için getter metodu.
    public static WebDriver getDriver() {
        return driver.get();
    }

    // Browser parametresine göre ilgili driver'ı başlatıyoruz.
    public static void setDriver(String browser) {
        WebDriver webDriver;

        switch (browser.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                webDriver = new FirefoxDriver();
                break;
            case "chrome":
            default:
                WebDriverManager.chromedriver().browserVersion("133.0.6943.99").setup();
                webDriver = new ChromeDriver();
                break;
        }
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.set(webDriver);
    }

    // Test tamamlandıktan sonra driver'ı kapatıp ThreadLocal'dan temizliyoruz.
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
