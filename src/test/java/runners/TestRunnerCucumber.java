package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import utils.DriverFactory;

/**
 * The TestRunnerCucumber class is responsible for running Cucumber test cases
 * using TestNG as the execution framework. It manages the WebDriver setup
 * before running tests and ensures proper cleanup afterward.
 */
@CucumberOptions(
        features = "src/test/resources/features", // Specifies the path where feature files are located
        glue = {"steps"}, // Defines the package containing step definitions
        plugin = {"pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"}, // Enables formatted output and Allure reporting
        monochrome = true // Enhances console output readability
)
public class TestRunnerCucumber extends AbstractTestNGCucumberTests {

    /**
     * Initializes the WebDriver before executing the test suite.
     * This method is executed once before all tests start running.
     *
     * @param browser The browser type provided via TestNG XML parameters.
     */
    @BeforeClass(alwaysRun = true)
    @Parameters({"browser"})
    public void setUp(String browser) {
        DriverFactory.setDriver(browser);
    }

    /**
     * Cleans up the WebDriver instance after all tests are completed.
     * Ensures that the browser is closed properly to free up system resources.
     */
    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (DriverFactory.getDriver() != null) { // Ensures the WebDriver is initialized before quitting
            DriverFactory.quitDriver();
        }
    }
}
