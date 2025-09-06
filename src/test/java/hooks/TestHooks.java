package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utilities.DriverFactory;
import utilities.PropertyReader;
import utilities.ScreenshotUtils;
import utilities.ExcelUtils;

public class TestHooks {

    @Before
    public void setUp(Scenario scenario) {
        String browser = PropertyReader.getBrowser();
        DriverFactory.initializeDriver(browser);

        String url = PropertyReader.getAppUrl();
        DriverFactory.getDriver().get(url);
    }

    @After
    public void tearDown(Scenario scenario) {
        if (DriverFactory.isDriverInitialized()) {
            try {
                String scenarioName = scenario.getName().replaceAll("[^a-zA-Z0-9-_]", "_");
                byte[] screenshotBytes = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);

                if (scenario.isFailed()) {
                    ScreenshotUtils.captureScreenshotOnFailure(DriverFactory.getDriver(), scenarioName);
                    scenario.attach(screenshotBytes, "image/png", scenarioName + "_FAIL");
                } else {
                    ScreenshotUtils.captureScreenshotOnPass(DriverFactory.getDriver(), scenarioName);
                    scenario.attach(screenshotBytes, "image/png", scenarioName + "_PASS");
                }

            } catch (Exception e) {
                System.out.println("Screenshot capture failed: " + e.getMessage());
            } finally {
                DriverFactory.quitDriver();
            }
        }
    }

    @After(order = 1000)
    public void cleanupResources(Scenario scenario) {
        try {
            ExcelUtils.closeWorkbooks();
        } catch (Exception e) {
            System.out.println("Error closing Excel workbooks: " + e.getMessage());
        }
    }
}