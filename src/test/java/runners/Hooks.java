package runners;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import utils.DriverFactory;

public class Hooks {

    /**
     * This method runs before each test scenario.
     * It ensures that the WebDriver instance is properly initialized.
     */
    @Before
    public void setUp() {
        if (DriverFactory.getDriver() == null) {  // Ensure the driver is not already initialized
            DriverFactory.setDriver("chrome"); // Default browser
            System.out.println("🚀 WebDriver initialized before test scenario.");
        }
    }

    /**
     * This method runs after each test scenario.
     * It ensures that the WebDriver instance is properly closed after test execution.
     */
    @After
    public void tearDown() {
        if (DriverFactory.getDriver() != null) {
            DriverFactory.quitDriver();
            System.out.println("🛑 WebDriver closed after test scenario.");
        }
    }
}
