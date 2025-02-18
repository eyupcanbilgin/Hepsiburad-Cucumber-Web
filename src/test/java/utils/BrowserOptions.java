package utils;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import java.util.HashMap;
import java.util.Map;

/**
 * The BrowserOptions class provides customized configurations for different browsers.
 * It includes settings for Chrome and Firefox to optimize the test execution environment.
 */
public class BrowserOptions {

    /**
     * Configures and returns ChromeOptions with predefined settings.
     *
     * @return A configured ChromeOptions instance.
     */
    public static ChromeOptions chromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        MutableCapabilities capabilities = new MutableCapabilities();

        // Browser preferences
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.cookies", 1); // Accept cookies
        prefs.put("profile.default_content_setting_values.notifications", 2); // Disable notifications
        chromeOptions.setExperimentalOption("prefs", prefs);

        // Browser arguments for better automation
        chromeOptions.addArguments("--disable-notifications"); // Disable web notifications
        chromeOptions.addArguments("--disable-popup-blocking"); // Prevent pop-ups from blocking tests
        chromeOptions.addArguments("--start-maximized"); // Start the browser in maximized mode
        chromeOptions.addArguments("--ignore-certificate-errors"); // Ignore SSL certificate errors
        chromeOptions.addArguments("--incognito"); // Run in incognito mode

        // Set capabilities to accept insecure certificates
        capabilities.setCapability("acceptInsecureCerts", true);
        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions); // Merge Chrome options with capabilities

        chromeOptions.merge(capabilities);
        return chromeOptions;
    }

    /**
     * Configures and returns FirefoxOptions with predefined settings.
     *
     * @return A configured FirefoxOptions instance.
     */
    public static FirefoxOptions firefoxOptions() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        MutableCapabilities capabilities = new MutableCapabilities();

        // Browser arguments for better automation
        firefoxOptions.addArguments("--private"); // Run in private mode (can be removed if not needed)
        firefoxOptions.addArguments("--disable-notifications"); // Disable notifications

        // Ensure fullscreen mode is properly set
        firefoxOptions.addPreference("browser.fullscreen.autohide", true);
        firefoxOptions.addPreference("browser.fullscreen.animate", false);

        // Set capabilities to accept insecure certificates
        capabilities.setCapability("acceptInsecureCerts", true);
        capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, firefoxOptions);

        firefoxOptions.merge(capabilities);
        return firefoxOptions;
    }
}
