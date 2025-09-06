package testRunners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepDefinitions", "hooks"},
        tags = "@Positive or @Negative",
        plugin = {
                "pretty",
                "html:reports/cucumber-html-report",
                "json:reports/cucumber-json-report.json",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        monochrome = true
)
public class RegressionTestRunner extends AbstractTestNGCucumberTests {
}