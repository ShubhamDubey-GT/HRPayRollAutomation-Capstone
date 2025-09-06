package testRunners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepDefinitions", "hooks"},
        tags = "@Smoke",
        plugin = {
                "pretty",
                "html:reports/cucumber-html",
                "json:reports/cucumber-json/report.json",
                "junit:reports/cucumber-junit-report.xml",
                "tech.grasshopper.extent.ExtentCucumberAdapter:"
        },
        monochrome = true,
        dryRun = false
)
public class SmokeTestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}