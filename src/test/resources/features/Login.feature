@Login @Smoke
Feature: HR Payroll System Login Functionality - Data Driven

  Background:
    Given I am on the HR Payroll login page

  @Positive @Critical
  Scenario Outline: Login with different user types
    When I enter login credentials for "<UserType>"
    And I click the login button
    Then login result should be "<Expected>"

    Examples:
      | UserType | Expected |
      | Admin    | Success  |

  @Negative
  Scenario Outline: Login with invalid credentials
    When I enter login credentials for "<UserType>"
    And I click the login button
    Then login result should be "<Expected>"

    Examples:
      | UserType    | Expected |
      | InvalidUser | Failure  |
      | EmptyUser   | Failure  |