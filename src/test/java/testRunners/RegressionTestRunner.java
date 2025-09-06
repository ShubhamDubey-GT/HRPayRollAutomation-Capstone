package testRunners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepDefinitions", "hooks"},
        tags = "@Positive or @Negative",
        plugin = {
                "pretty",
                "html:reports/html",
                "json:reports/json/cucumber-report.json",
                "tech.grasshopper.extent.ExtentCucumberAdapter:"
        },
        monochrome = true
)
public class RegressionTestRunner extends AbstractTestNGCucumberTests {
}