package stepDefinitions;

import io.cucumber.java.en.*;
import pages.DashboardPage;
import pages.AdminPage;
import pages.PIMPage;
import pages.LeavePage;
import utilities.DriverFactory;
import utilities.ExcelUtils;
import org.testng.Assert;
import java.util.Map;

public class NavigationSteps {
    private DashboardPage dashboardPage;
    private AdminPage adminPage;
    private PIMPage pimPage;
    private LeavePage leavePage;

    @When("I navigate to {string} module")
    public void i_navigate_to_module(String moduleName) {
        dashboardPage = new DashboardPage(DriverFactory.getDriver());

        switch (moduleName) {
            case "Admin":
                adminPage = dashboardPage.navigateToAdmin();
                break;
            case "PIM":
                pimPage = dashboardPage.navigateToPIM();
                break;
            case "Leave":
                leavePage = dashboardPage.navigateToLeave();
                break;
            default:
                throw new RuntimeException("Unknown module: " + moduleName);
        }
    }

    @Then("navigation result should be {string}")
    public void navigation_result_should_be(String expectedResult) {
        switch (expectedResult) {
            case "AdminPageDisplayed":
                Assert.assertTrue(adminPage.isAdminPageDisplayed(), "Admin page should be displayed");
                break;
            case "PIMPageDisplayed":
                Assert.assertTrue(pimPage.isPIMPageDisplayed(), "PIM page should be displayed");
                break;
            case "LeavePageDisplayed":
                Assert.assertTrue(leavePage.isLeavePageDisplayed(), "Leave page should be displayed");
                break;
            default:
                throw new RuntimeException("Unknown expected result: " + expectedResult);
        }
    }

    @When("I navigate to Admin module")
    public void i_navigate_to_admin_module() {
        i_navigate_to_module("Admin");
    }

    @When("I navigate to PIM module")
    public void i_navigate_to_pim_module() {
        i_navigate_to_module("PIM");
    }

    @When("I navigate to Leave module")
    public void i_navigate_to_leave_module() {
        i_navigate_to_module("Leave");
    }

    @Then("Admin page should be displayed")
    public void admin_page_should_be_displayed() {
        Assert.assertTrue(adminPage.isAdminPageDisplayed(), "Admin page should be displayed");
    }

    @Then("PIM page should be displayed")
    public void pim_page_should_be_displayed() {
        Assert.assertTrue(pimPage.isPIMPageDisplayed(), "PIM page should be displayed");
    }

    @Then("Leave page should be displayed")
    public void leave_page_should_be_displayed() {
        Assert.assertTrue(leavePage.isLeavePageDisplayed(), "Leave page should be displayed");
    }
}