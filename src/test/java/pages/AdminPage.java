package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilities.ElementUtils;
import utilities.WaitUtils;

public class AdminPage extends BasePage {

    private static final By ADMIN_HEADER = By.xpath("//h6[text()='Admin']");
    private static final By ADD_BUTTON = By.xpath("//button[text()=' Add ']");

    public AdminPage(WebDriver driver) {
        super(driver);
    }

    public boolean isAdminPageDisplayed() {
        try {
            WaitUtils.waitForElementVisible(driver, ADMIN_HEADER);
            System.out.println("Admin page is displayed");
            return true;
        } catch (Exception e) {
            System.out.println("Admin page not found");
            return false;
        }
    }

    public void clickAddButton() {
        if (ElementUtils.isDisplayed(driver, ADD_BUTTON)) {
            System.out.println("➕ Clicking Add button");
            ElementUtils.click(driver, ADD_BUTTON);
        } else {
            System.out.println("Add button not available (Demo limitation)");
        }
    }
}