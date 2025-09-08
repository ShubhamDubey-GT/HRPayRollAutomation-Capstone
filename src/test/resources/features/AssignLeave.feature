@Smoke
Feature: Assign Leave to Employees
  As an authorized HR/Admin user
  I want to assign leave to employees
  So that leave requests are tracked and managed properly

  Background:
    Given I am logged into the application as "Admin"
    And I navigate to "Leave" module
    And I am on the "Assign Leave" page

  @Positive @Leave
  Scenario Outline: Assign leave with various positive scenarios
    When I assign leave using test data "<Scenario>"
    Then leave should be assigned successfully
    And success message should be displayed

    Examples:
      | Scenario          |
      | ValidBasicLeave   |
      | SingleDayLeave    |
      | FutureDateLeave   |
      | LeaveWithBalance  |
      | AnnualLeaveRequest|
      | SickLeaveRequest  |

  @Negative @Leave
  Scenario Outline: Assign leave with validation errors
    When I assign leave using test data "<Scenario>"
    Then I should see error message from test data

    Examples:
      | Scenario                |
      | EmptyEmployeeName       |
      | EmptyLeaveType          |
      | EmptyFromDate           |
      | EmptyToDate             |
      | InvalidEmployee         |
      | InsufficientBalance     |
      | FromDateGreaterToDate   |
      | PastDateRestriction     |
      | OverlappingLeave        |
      | InvalidDateFormat       |
      | LongComments            |

  @Negative @Leave
  Scenario: Unauthorized user trying to assign leave
    Given I am logged into the application as "ESS"
    And I navigate to "Leave" module
    And I am on the "Assign Leave" page
    When I assign leave using test data "UnauthorizedAccess"
    Then I should see error message from test data

  @Positive @Leave
  Scenario Outline: Assign different types of leave
    When I assign leave using test data "<Scenario>"
    Then leave should be assigned successfully
    And leave balance should be updated correctly

    Examples:
      | Scenario        |
      | MaternityLeave  |
      | EmergencyLeave  |
      | CasualLeave     |