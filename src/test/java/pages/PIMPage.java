package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilities.ElementUtils;
import utilities.WaitUtils;

public class PIMPage extends BasePage {

    // FIXED: Simple, working locators for PIM functionality
    private static final By PIM_HEADER = By.xpath("//h6[text()='PIM']");
    private static final By ADD_EMPLOYEE_LINK = By.xpath("//a[text()='Add Employee']");
    private static final By FIRST_NAME_FIELD = By.name("firstName");
    private static final By MIDDLE_NAME_FIELD = By.name("middleName");
    private static final By LAST_NAME_FIELD = By.name("lastName");
    private static final By SAVE_BUTTON = By.xpath("//button[@type='submit']");
    private static final By SUCCESS_MESSAGE = By.xpath("//p[contains(@class,'oxd-text--toast-message')]");
    private static final By ERROR_MESSAGES = By.xpath("//span[contains(@class,'oxd-input-field-error-message')]");

    public PIMPage(WebDriver driver) {
        super(driver);
    }

    public boolean isPIMPageDisplayed() {
        try {
            WaitUtils.waitForElementVisible(driver, PIM_HEADER);
            System.out.println("PIM page is displayed");
            return true;
        } catch (Exception e) {
            System.out.println("PIM page not found");
            return false;
        }
    }

    public void clickAddEmployee() {
        System.out.println("Clicking Add Employee");
        ElementUtils.click(driver, ADD_EMPLOYEE_LINK);
        WaitUtils.waitForSeconds(3); // Wait for form to load
    }

    public void enterFirstName(String firstName) {
        System.out.println("Entering first name: " + firstName);
        ElementUtils.type(driver, FIRST_NAME_FIELD, firstName);
    }

    public void enterMiddleName(String middleName) {
        System.out.println("Entering middle name: " + middleName);
        ElementUtils.type(driver, MIDDLE_NAME_FIELD, middleName);
    }

    public void enterLastName(String lastName) {
        System.out.println("Entering last name: " + lastName);
        ElementUtils.type(driver, LAST_NAME_FIELD, lastName);
    }

    public void clickSaveButton() {
        System.out.println("Clicking Save button");
        ElementUtils.click(driver, SAVE_BUTTON);
        WaitUtils.waitForSeconds(3); // Wait for save operation
    }

    public boolean isSuccessMessageDisplayed() {
        try {
            WaitUtils.waitForElementVisible(driver, SUCCESS_MESSAGE);
            System.out.println("Success message displayed");
            return true;
        } catch (Exception e) {
            System.out.println("Success message not found");
            return false;
        }
    }

    public String getSuccessMessage() {
        try {
            return ElementUtils.getText(driver, SUCCESS_MESSAGE);
        } catch (Exception e) {
            return "";
        }
    }

    public boolean areErrorMessagesDisplayed() {
        return ElementUtils.isDisplayed(driver, ERROR_MESSAGES);
    }

    // Simple method that works on demo
    public void addBasicEmployee(String firstName, String lastName) {
        clickAddEmployee();
        enterFirstName(firstName);
        enterLastName(lastName);
        clickSaveButton();
    }

    // Method with middle name
    public void addEmployee(String firstName, String middleName, String lastName) {
        clickAddEmployee();
        enterFirstName(firstName);
        if (middleName != null && !middleName.trim().isEmpty()) {
            enterMiddleName(middleName);
        }
        enterLastName(lastName);
        clickSaveButton();
    }
}