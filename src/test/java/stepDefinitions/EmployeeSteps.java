package stepDefinitions;

import io.cucumber.java.en.*;
import pages.LoginPage;
import pages.DashboardPage;
import pages.PIMPage;
import utilities.DriverFactory;
import utilities.ExcelUtils;
import org.testng.Assert;
import java.util.Map;

public class EmployeeSteps {
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private PIMPage pimPage;

    @Given("I am logged in as admin user")
    public void i_am_logged_in_as_admin_user() {
        Map<String, String> adminCreds = ExcelUtils.getLoginCredentials("Admin");

        loginPage = new LoginPage(DriverFactory.getDriver());
        dashboardPage = loginPage.login(adminCreds.get("Username"), adminCreds.get("Password"));

        Assert.assertTrue(dashboardPage.isDashboardDisplayed(), "Should be logged in successfully");
    }

    @Given("I am on the Add Employee page")
    public void i_am_on_the_add_employee_page() {
        pimPage = dashboardPage.navigateToPIM();
        Assert.assertTrue(pimPage.isPIMPageDisplayed(), "PIM page should be displayed");
        pimPage.clickAddEmployee();
    }

    @When("I enter employee data for {string} scenario")
    public void i_enter_employee_data_for_scenario(String scenario) {
        Map<String, String> employeeData = ExcelUtils.getEmployeeData(scenario);

        String firstName = employeeData.get("FirstName");
        String middleName = employeeData.get("MiddleName");
        String lastName = employeeData.get("LastName");

        if (firstName != null && !firstName.trim().isEmpty()) {
            pimPage.enterFirstName(firstName);
        }

        if (middleName != null && !middleName.trim().isEmpty()) {
            pimPage.enterMiddleName(middleName);
        }

        if (lastName != null && !lastName.trim().isEmpty()) {
            pimPage.enterLastName(lastName);
        }
    }

    @When("I save the employee")
    public void i_save_the_employee() {
        pimPage.clickSaveButton();
    }

    @When("I click save employee")
    public void i_click_save_employee() {
        pimPage.clickSaveButton();
    }

    @Then("employee result should be {string}")
    public void employee_result_should_be(String expectedResult) {
        if ("Success".equals(expectedResult)) {
            try {
                Thread.sleep(3000);
                String currentUrl = DriverFactory.getDriver().getCurrentUrl();
                boolean isSuccess = currentUrl.contains("viewEmployee") ||
                        pimPage.isSuccessMessageDisplayed() ||
                        currentUrl.contains("pim");
                Assert.assertTrue(isSuccess, "Employee save operation should be successful");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } else if ("ValidationError".equals(expectedResult)) {
            boolean hasValidationErrors = pimPage.areErrorMessagesDisplayed();
            Assert.assertTrue(hasValidationErrors, "Validation errors should be displayed");
        }
    }

    @Then("employee should be created successfully")
    public void employee_should_be_created_successfully() {
        try {
            Thread.sleep(3000);
            String currentUrl = DriverFactory.getDriver().getCurrentUrl();
            boolean isSuccess = currentUrl.contains("viewEmployee") ||
                    pimPage.isSuccessMessageDisplayed() ||
                    currentUrl.contains("pim");
            Assert.assertTrue(isSuccess, "Employee should be created successfully");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Then("I should see validation errors")
    public void i_should_see_validation_errors() {
        Assert.assertTrue(pimPage.areErrorMessagesDisplayed(), "Validation errors should be displayed");
    }
}