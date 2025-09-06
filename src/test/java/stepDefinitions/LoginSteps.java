package stepDefinitions;

import io.cucumber.java.en.*;
import pages.LoginPage;
import pages.DashboardPage;
import utilities.DriverFactory;
import utilities.ExcelUtils;
import org.testng.Assert;
import java.util.Map;

public class LoginSteps {
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private Map<String, String> testData;

    @Given("I am on the HR Payroll login page")
    public void i_am_on_the_hr_payroll_login_page() {
        // Page is already loaded in hooks
        loginPage = new LoginPage(DriverFactory.getDriver());
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page should be displayed");
    }

    @When("I enter login credentials for {string}")
    public void i_enter_login_credentials_for(String userType) {
        System.out.println("Getting login credentials for user type: " + userType);
        testData = ExcelUtils.getLoginCredentials(userType);

        String username = testData.get("Username");
        String password = testData.get("Password");

        System.out.println("Entering username: " + username);
        loginPage.enterUsername(username);

        System.out.println("Entering password");
        loginPage.enterPassword(password);
    }

    @When("I click the login button")
    public void i_click_the_login_button() {
        System.out.println("Clicking login button");
        loginPage.clickLoginButton();
    }

    @Then("login result should be {string}")
    public void login_result_should_be(String expectedResult) {
        System.out.println("Validating login result should be: " + expectedResult);

        if ("Success".equals(expectedResult)) {
            dashboardPage = new DashboardPage(DriverFactory.getDriver());
            Assert.assertTrue(dashboardPage.isDashboardDisplayed(),
                    "Dashboard should be displayed for successful login");
            System.out.println("Login successful - Dashboard displayed");
        } else if ("Failure".equals(expectedResult)) {
            // Check if still on login page or error message shown
            boolean loginFailed = loginPage.isErrorMessageDisplayed() ||
                    loginPage.isLoginPageDisplayed() ||
                    DriverFactory.getDriver().getCurrentUrl().contains("login");
            Assert.assertTrue(loginFailed, "Login should be denied for invalid credentials");
            System.out.println("Login failed as expected");
        }
    }

    @Then("I should be redirected to the dashboard")
    public void i_should_be_redirected_to_the_dashboard() {
        dashboardPage = new DashboardPage(DriverFactory.getDriver());
        Assert.assertTrue(dashboardPage.isDashboardDisplayed(), "Dashboard should be displayed");
        System.out.println("Successfully redirected to dashboard");
    }

    @Then("I should see the {string} page")
    public void i_should_see_the_page(String expectedPage) {
        if (expectedPage.equals("Dashboard")) {
            dashboardPage = new DashboardPage(DriverFactory.getDriver());
            Assert.assertTrue(dashboardPage.isDashboardDisplayed(), "Dashboard should be displayed");
            System.out.println("Dashboard page is displayed");
        }
    }

    @Then("access should be denied")
    public void access_should_be_denied() {
        boolean accessDenied = loginPage.isErrorMessageDisplayed() ||
                loginPage.isLoginPageDisplayed() ||
                DriverFactory.getDriver().getCurrentUrl().contains("login");
        Assert.assertTrue(accessDenied, "Login should be denied");
        System.out.println("Access denied as expected");
    }

    @Then("I should remain on the login page")
    public void i_should_remain_on_the_login_page() {
        Assert.assertTrue(loginPage.isLoginPageDisplayed() ||
                        DriverFactory.getDriver().getCurrentUrl().contains("login"),
                "Should remain on login page");
        System.out.println("Remained on login page as expected");
    }

    // Helper method to validate expected result matches actual result
    private void validateExpectedResult(String expectedFromData) {
        if (testData != null && testData.containsKey("Expected")) {
            String expectedFromExcel = testData.get("Expected");
            Assert.assertEquals(expectedFromExcel, expectedFromData,
                    "Expected result from step should match Excel data");
        }
    }
}