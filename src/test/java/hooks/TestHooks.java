package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utilities.DriverFactory;
import utilities.PropertyReader;
import utilities.ScreenshotUtils;

public class TestHooks {

    @Before
    public void setUp(Scenario scenario) {
        System.out.println("Starting scenario: " + scenario.getName());
        String browser = System.getProperty("browser", PropertyReader.getBrowser());
        DriverFactory.initializeDriver(browser);

        // Navigate to application URL
        String url = PropertyReader.getAppUrl();
        DriverFactory.getDriver().get(url);
        System.out.println("🌐 Navigated to: " + url);
    }


    @After
    public void tearDown(Scenario scenario) {
        if (DriverFactory.isDriverInitialized()) {
            try {
                String safeName = scenario.getName().replaceAll("[^a-zA-Z0-9-_]", "_");
                byte[] bytes = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);

                if (scenario.isFailed()) {
                    ScreenshotUtils.captureScreenshotOnFailure(DriverFactory.getDriver(), safeName);
                    scenario.attach(bytes, "image/png", safeName + "_FAIL");
                    System.out.println("Scenario failed: " + scenario.getName());
                } else {
                    ScreenshotUtils.captureScreenshotOnPass(DriverFactory.getDriver(), safeName);
                    scenario.attach(bytes, "image/png", safeName + "_PASS");
                    System.out.println("Scenario passed: " + scenario.getName());
                }
            } catch (Exception e) {
                System.out.println("Screenshot capture failed: " + e.getMessage());
            } finally {
                DriverFactory.quitDriver();
            }
        }
    }

}