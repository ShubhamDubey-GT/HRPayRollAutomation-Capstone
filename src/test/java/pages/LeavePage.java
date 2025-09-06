package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilities.ElementUtils;
import utilities.WaitUtils;

public class LeavePage extends BasePage {

    private static final By LEAVE_HEADER = By.xpath("//h6[text()='Leave']");
    private static final By ASSIGN_LEAVE_LINK = By.xpath("//a[text()='Assign Leave']");

    public LeavePage(WebDriver driver) {
        super(driver);
    }

    public boolean isLeavePageDisplayed() {
        try {
            WaitUtils.waitForElementVisible(driver, LEAVE_HEADER);
            System.out.println("Leave page is displayed");
            return true;
        } catch (Exception e) {
            System.out.println("Leave page not found");
            return false;
        }
    }

    public void clickAssignLeave() {
        if (ElementUtils.isDisplayed(driver, ASSIGN_LEAVE_LINK)) {
            System.out.println("Clicking Assign Leave");
            ElementUtils.click(driver, ASSIGN_LEAVE_LINK);
        } else {
            System.out.println("Assign Leave not available (Demo limitation)");
        }
    }
}