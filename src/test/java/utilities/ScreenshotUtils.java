package utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {
    private static final String DIR = "screenshots";

    public static String captureWithSuffix(WebDriver driver, String baseName, String suffix) {
        try {
            String safeName = baseName.replaceAll("[^a-zA-Z0-9-_]", "_");
            String ts = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
            String fileName = safeName + suffix + "_" + ts + ".png";
            File outDir = new File(DIR);
            if (!outDir.exists()) outDir.mkdirs();
            File dest = new File(outDir, fileName);
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, dest);
            return dest.getPath();
        } catch (Exception e) {
            throw new RuntimeException("Failed to capture screenshot", e);
        }
    }

    public static String captureScreenshotOnFailure(WebDriver driver, String baseName) {
        return captureWithSuffix(driver, baseName, "_FAIL");
    }

    public static String captureScreenshotOnPass(WebDriver driver, String baseName) {
        return captureWithSuffix(driver, baseName, "_PASS");
    }

    public static String takeScreenshot(String testName) {
        try {
            return captureWithSuffix(DriverFactory.getDriver(), testName, "_TEST");
        } catch (Exception e) {
            System.err.println("Failed to take screenshot: " + e.getMessage());
            return null;
        }
    }
}
