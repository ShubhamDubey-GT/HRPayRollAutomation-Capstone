package testRunners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"stepDefinitions", "hooks"},
    tags = "@Positive",
    plugin = {
            "pretty",
            "html:reports/cucumber-html",
            "json:reports/cucumber-json/report.json",
            "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
    },
    monochrome = true
)
public class PositiveTestRunner extends AbstractTestNGCucumberTests {
}