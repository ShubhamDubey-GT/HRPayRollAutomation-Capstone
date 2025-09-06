package testRunners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepDefinitions", "hooks"},
        tags = "@Negative",
        plugin = {
                "pretty",
                "html:reports/html",
                "json:reports/json/cucumber-report.json",
                "tech.grasshopper.extent.ExtentCucumberAdapter:"
        },
        monochrome = true
)
public class NegativeTestRunner extends AbstractTestNGCucumberTests {
}