package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {
    private static Properties properties = new Properties();
    private static final String CONFIG_PATH = "src/test/resources/config/config.properties";

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (FileInputStream fis = new FileInputStream(CONFIG_PATH)) {
            properties.load(fis);
            System.out.println("Configuration loaded successfully from: " + CONFIG_PATH);
        } catch (IOException e) {
            System.out.println("Config file not found, using defaults");
            setDefaultProperties();
        }
    }

    private static void setDefaultProperties() {
        properties.setProperty("app.url", "https://opensource-demo.orangehrmlive.com");
        properties.setProperty("browser", "chrome");
        properties.setProperty("username", "Admin");
        properties.setProperty("password", "admin123");
        properties.setProperty("timeout", "30");
        properties.setProperty("useGrid", "false");
        properties.setProperty("selenium.grid.url", "http://localhost:4444/wd/hub");
    }

    public static String getProperty(String key) {
        String systemProperty = System.getProperty(key);
        if (systemProperty != null && !systemProperty.isEmpty()) {
            return systemProperty;
        }

        String envProperty = System.getenv(key.toUpperCase().replace(".", "_"));
        if (envProperty != null && !envProperty.isEmpty()) {
            return envProperty;
        }

        return properties.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        String value = getProperty(key);
        return value != null ? value : defaultValue;
    }

    public static String getAppUrl() {
        return getProperty("app.url");
    }

    public static String getBrowser() {
        return getProperty("browser", "chrome");
    }

    public static String getUsername() {
        return getProperty("username", "Admin");
    }

    public static String getPassword() {
        return getProperty("password", "admin123");
    }

    public static int getTimeout() {
        return Integer.parseInt(getProperty("timeout", "30"));
    }

    public static boolean useGrid() {
        return Boolean.parseBoolean(getProperty("useGrid", "false"));
    }

    public static String getGridUrl() {
        return getProperty("selenium.grid.url", "http://localhost:4444/wd/hub");
    }
}