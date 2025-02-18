package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import utils.DriverFactory;

/**
 * The TestRunner class is responsible for executing the Cucumber test cases
 * using TestNG as the test framework. It sets up the WebDriver before running
 * the tests and ensures proper cleanup after execution.
 */
@CucumberOptions(
        features = "src/test/resources/features", // Specifies the location of feature files
        glue = {"steps"}, // Specifies the package where step definitions are located
        plugin = {"pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"}, // Enables reporting with Allure
        monochrome = true // Improves console output readability
)
public class TestRunner extends AbstractTestNGCucumberTests {

    /**
     * Sets up the WebDriver before running the test suite.
     * This method is executed once before any tests are run.
     *
     * @param browser The browser parameter passed from the TestNG XML configuration file.
     */
    @BeforeClass(alwaysRun = true)
    @Parameters({"browser"})
    public void setUp(String browser) {
        DriverFactory.setDriver(browser);
    }

    /**
     * Cleans up the WebDriver after all tests have been executed.
     * This method ensures that the WebDriver instance is properly closed.
     */
    @AfterClass(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
