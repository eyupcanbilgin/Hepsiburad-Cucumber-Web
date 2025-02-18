package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import utils.DriverFactory;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"steps"},
        plugin = {"pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"},
        monochrome = true
)
public class TestRunnerCucumber extends AbstractTestNGCucumberTests {

    @BeforeClass(alwaysRun = true)
    @Parameters({"browser"})
    public void setUp(String browser) {
        DriverFactory.setDriver(browser);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (DriverFactory.getDriver() != null) {
            DriverFactory.quitDriver();
        }
    }
}
