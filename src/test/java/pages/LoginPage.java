package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilities.ElementUtils;
import utilities.WaitUtils;
import utilities.PropertyReader;

public class LoginPage extends BasePage {

    // FIXED: These locators are verified to work on OrangeHRM demo
    private static final By USERNAME_FIELD = By.name("username");
    private static final By PASSWORD_FIELD = By.name("password");
    private static final By LOGIN_BUTTON = By.xpath("//button[@type='submit']");
    private static final By ERROR_MESSAGE = By.xpath("//p[contains(@class,'oxd-alert-content-text')]");
    private static final By LOGIN_PANEL = By.xpath("//h5[text()='Login']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void enterUsername(String username) {
        System.out.println("Entering username: " + username);
        ElementUtils.type(driver, USERNAME_FIELD, username);
    }

    public void enterPassword(String password) {
        System.out.println("Entering password");
        ElementUtils.type(driver, PASSWORD_FIELD, password);
    }

    public void clickLoginButton() {
        System.out.println("Clicking login button");
        ElementUtils.click(driver, LOGIN_BUTTON);
        WaitUtils.waitForSeconds(3); // Wait for navigation
    }

    public DashboardPage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        return new DashboardPage(driver);
    }

    public DashboardPage loginWithDefaults() {
        return login(PropertyReader.getUsername(), PropertyReader.getPassword());
    }

    public String getErrorMessage() {
        try {
            WaitUtils.waitForElementVisible(driver, ERROR_MESSAGE);
            return ElementUtils.getText(driver, ERROR_MESSAGE);
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isLoginPageDisplayed() {
        return ElementUtils.isDisplayed(driver, LOGIN_PANEL);
    }

    public boolean isErrorMessageDisplayed() {
        return ElementUtils.isDisplayed(driver, ERROR_MESSAGE);
    }

    public void clearFields() {
        try {
            ElementUtils.type(driver, USERNAME_FIELD, "");
            ElementUtils.type(driver, PASSWORD_FIELD, "");
        } catch (Exception e) {
            System.out.println("Could not clear fields");
        }
    }
}