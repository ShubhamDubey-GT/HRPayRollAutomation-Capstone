package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilities.ElementUtils;
import utilities.WaitUtils;

public class DashboardPage extends BasePage {

    // FIXED: Working locators for OrangeHRM dashboard
    private static final By DASHBOARD_HEADER = By.xpath("//h6[text()='Dashboard']");
    private static final By USER_DROPDOWN = By.xpath("//p[@class='oxd-userdropdown-name']");
    private static final By LOGOUT_LINK = By.xpath("//a[text()='Logout']");
    private static final By ADMIN_MENU = By.xpath("//span[text()='Admin']");
    private static final By PIM_MENU = By.xpath("//span[text()='PIM']");
    private static final By LEAVE_MENU = By.xpath("//span[text()='Leave']");
    private static final By ASSIGN_LEAVE_SUBMENU = By.xpath("//a[text()='Assign Leave']");

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public boolean isDashboardDisplayed() {
        try {
            WaitUtils.waitForElementVisible(driver, DASHBOARD_HEADER);
            System.out.println("Dashboard is displayed");
            return true;
        } catch (Exception e) {
            System.out.println("Dashboard not found");
            return false;
        }
    }

    public String getDashboardHeader() {
        return ElementUtils.getText(driver, DASHBOARD_HEADER);
    }

    public AdminPage navigateToAdmin() {
        System.out.println("Navigating to Admin module");
        ElementUtils.click(driver, ADMIN_MENU);
        WaitUtils.waitForSeconds(2);
        return new AdminPage(driver);
    }

    public PIMPage navigateToPIM() {
        System.out.println("Navigating to PIM module");
        ElementUtils.click(driver, PIM_MENU);
        WaitUtils.waitForSeconds(2);
        return new PIMPage(driver);
    }

    public LeavePage navigateToLeave() {
        System.out.println("Navigating to Leave module");
        ElementUtils.click(driver, LEAVE_MENU);
        WaitUtils.waitForSeconds(2);
        return new LeavePage(driver);
    }

    public LoginPage logout() {
        System.out.println("Logging out");
        ElementUtils.click(driver, USER_DROPDOWN);
        WaitUtils.waitForElementClickable(driver, LOGOUT_LINK);
        ElementUtils.click(driver, LOGOUT_LINK);
        return new LoginPage(driver);
    }

    public boolean isLoginSuccessful() {
        return isDashboardDisplayed() && ElementUtils.isDisplayed(driver, USER_DROPDOWN);
    }
    public LeavePage navigateToAssignLeave() {
        System.out.println("Navigating to Assign Leave page");

        // First navigate to Leave module (uses your existing method)
        LeavePage leavePage = navigateToLeave();

        // Then click on Assign Leave
        leavePage.clickAssignLeave();

        // Wait for assign leave form to load
        WaitUtils.waitForSeconds(2);

        return leavePage;
    }

    public boolean isAssignLeaveAccessible() {
        try {
            // Navigate to Leave first
            navigateToLeave();
            WaitUtils.waitForSeconds(1);

            // Use waitForElement for timeout, then check if displayed
            if (ElementUtils.waitForElement(driver, ASSIGN_LEAVE_SUBMENU, 3)) {
                return ElementUtils.isDisplayed(driver, ASSIGN_LEAVE_SUBMENU);
            }
            return false;

        } catch (Exception e) {
            System.out.println("Assign Leave not accessible: " + e.getMessage());
            return false;
        }
    }

}