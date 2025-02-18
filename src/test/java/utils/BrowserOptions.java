package utils;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.HashMap;
import java.util.Map;

public class BrowserOptions {

    public static ChromeOptions chromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        MutableCapabilities capabilities = new MutableCapabilities();

        // Tarayıcı tercihleri
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.cookies", 1); // Çerezleri kabul et
        prefs.put("profile.default_content_setting_values.notifications", 2); // Bildirimleri kapat
        chromeOptions.setExperimentalOption("prefs", prefs);

        // Tarayıcı ayarları
        chromeOptions.addArguments("--disable-notifications"); // Web bildirimlerini engelle
        chromeOptions.addArguments("--disable-popup-blocking"); // Popup engelleyiciyi kapat
        chromeOptions.addArguments("--start-maximized"); // Tarayıcıyı tam ekran aç
        chromeOptions.addArguments("--ignore-certificate-errors"); // SSL hatalarını yoksay
        chromeOptions.addArguments("--incognito"); // Gizli modda çalıştır

        capabilities.setCapability("acceptInsecureCerts", true); // Güvensiz sertifikaları kabul et
        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions); // ChromeOptions ile birleştir

        chromeOptions.merge(capabilities);
        return chromeOptions;
    }

    public static FirefoxOptions firefoxOptions() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        MutableCapabilities capabilities = new MutableCapabilities();

        // Bildirimleri kapat
        firefoxOptions.addPreference("permissions.default.desktop-notification", 2);
        firefoxOptions.addPreference("dom.webnotifications.enabled", false);

        // Tarayıcı ayarları
        firefoxOptions.addArguments("--private"); // Gizli modda çalıştır
        firefoxOptions.addArguments("--start-maximized"); // Tam ekran aç
        firefoxOptions.addArguments("--ignore-certificate-errors"); // SSL hatalarını yoksay

        capabilities.setCapability("acceptInsecureCerts", true);
        capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, firefoxOptions);

        firefoxOptions.merge(capabilities);
        return firefoxOptions;
    }
}
