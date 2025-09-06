package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class DriverFactory {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initializeDriver(String browserName) {
        WebDriver webDriver = null;
        boolean useGrid = PropertyReader.useGrid();

        System.out.println("Initializing " + browserName + " driver (Grid: " + useGrid + ")");

        if (useGrid) {
            webDriver = createRemoteDriver(browserName);
        } else {
            webDriver = createLocalDriver(browserName);
        }

        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(PropertyReader.getTimeout()));
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        driver.set(webDriver);
        System.out.println("Driver initialized successfully");
    }

    private static WebDriver createLocalDriver(String browserName) {
        switch (browserName.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--remote-allow-origins=*");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--disable-popup-blocking");

                if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
                    chromeOptions.addArguments("--headless");
                }

                if (Boolean.parseBoolean(System.getProperty("maximize", "false"))) {
                    chromeOptions.addArguments("--start-maximized");
                }
                return new ChromeDriver(chromeOptions);

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--disable-notifications");
                if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
                    firefoxOptions.addArguments("--headless");
                }
                return new FirefoxDriver(firefoxOptions);

            case "edge":
                WebDriverManager.edgedriver().setup();
                return new EdgeDriver();

            default:
                System.out.println("Browser not supported: " + browserName + ", defaulting to Chrome");
                return createLocalDriver("chrome");
        }
    }

    private static WebDriver createRemoteDriver(String browserName) {
        try {
            String gridUrl = PropertyReader.getGridUrl();
            System.out.println("🌐 Connecting to Grid: " + gridUrl);

            switch (browserName.toLowerCase()) {
                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    return new RemoteWebDriver(new URL(gridUrl), chromeOptions);

                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    return new RemoteWebDriver(new URL(gridUrl), firefoxOptions);

                default:
                    System.out.println("⚠️ Browser not supported in Grid: " + browserName + ", using Chrome");
                    ChromeOptions defaultOptions = new ChromeOptions();
                    return new RemoteWebDriver(new URL(gridUrl), defaultOptions);
            }
        } catch (MalformedURLException e) {
            System.out.println("Invalid Grid URL: " + PropertyReader.getGridUrl());
            System.out.println("Falling back to local driver");
            return createLocalDriver(browserName);
        }
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        WebDriver webDriver = driver.get();
        if (webDriver != null) {
            try {
                webDriver.quit();
                System.out.println("Driver closed successfully");
            } catch (Exception e) {
                System.out.println("Error closing driver: " + e.getMessage());
            }
            driver.remove();
        }
    }

    public static boolean isDriverInitialized() {
        return driver.get() != null;
    }
}