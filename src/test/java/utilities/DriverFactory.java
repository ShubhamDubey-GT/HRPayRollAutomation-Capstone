package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static boolean isDriverManagerSetup = false;
    
    public static void initializeDriver(String browserName) {
        if (driver.get() != null) {
            quitDriver();
        }
        
        // Setup WebDriverManager only once
        if (!isDriverManagerSetup) {
            setupWebDriverManager();
            isDriverManagerSetup = true;
        }
        
        WebDriver webDriver = createDriver(browserName.toLowerCase());
        driver.set(webDriver);
        
        // Configure window
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));
    }
    
    private static void setupWebDriverManager() {
        try {
            // Setup all drivers at once
            WebDriverManager.chromedriver().setup();
            WebDriverManager.firefoxdriver().setup();
            WebDriverManager.edgedriver().setup();
            System.out.println("WebDriverManager setup completed");
        } catch (Exception e) {
            System.out.println("WebDriverManager setup failed: " + e.getMessage());
        }
    }
    
    private static WebDriver createDriver(String browserName) {
        switch (browserName) {
            case "chrome":
                return createChromeDriver();
            case "firefox":
                return createFirefoxDriver();
            case "edge":
                return createEdgeDriver();
            default:
                throw new IllegalArgumentException("Browser not supported: " + browserName);
        }
    }
    
    private static WebDriver createChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        
        // GitHub Actions or headless mode
        if (System.getenv("GITHUB_ACTIONS") != null || 
            Boolean.parseBoolean(System.getProperty("headless", "false"))) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--window-size=1920,1080");
        }
        
        // Common options
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-notifications");
        
        try {
            return new ChromeDriver(options);
        } catch (Exception e) {
            System.out.println("Failed to create Chrome driver: " + e.getMessage());
            throw e;
        }
    }
    
    private static WebDriver createFirefoxDriver() {
        FirefoxOptions options = new FirefoxOptions();
        
        if (System.getenv("GITHUB_ACTIONS") != null || 
            Boolean.parseBoolean(System.getProperty("headless", "false"))) {
            options.addArguments("--headless");
        }
        
        return new FirefoxDriver(options);
    }
    
    private static WebDriver createEdgeDriver() {
        EdgeOptions options = new EdgeOptions();
        
        if (System.getenv("GITHUB_ACTIONS") != null || 
            Boolean.parseBoolean(System.getProperty("headless", "false"))) {
            options.addArguments("--headless");
        }
        
        return new EdgeDriver(options);
    }
    
    public static WebDriver getDriver() {
        WebDriver webDriver = driver.get();
        if (webDriver == null) {
            throw new RuntimeException("Driver not initialized. Call initializeDriver() first.");
        }
        return webDriver;
    }
    
    public static boolean isDriverInitialized() {
        return driver.get() != null;
    }
    
    public static void quitDriver() {
        WebDriver webDriver = driver.get();
        if (webDriver != null) {
            try {
                webDriver.quit();
            } catch (Exception e) {
                System.out.println("Error quitting driver: " + e.getMessage());
            } finally {
                driver.remove();
            }
        }
    }
}