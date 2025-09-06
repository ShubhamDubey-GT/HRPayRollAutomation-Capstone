@Employee @PIM
Feature: HR Payroll System Employee Management - Data Driven

  Background:
    Given I am logged in as admin user

  @Positive @Smoke
  Scenario Outline: Add employee with different data scenarios
    Given I am on the Add Employee page
    When I enter employee data for "<Scenario>" scenario
    And I save the employee
    Then employee result should be "<Expected>"

    Examples:
      | Scenario        | Expected |
      | PositiveBasic   | Success  |
      | PositiveFull    | Success  |
      | PositiveMinimal | Success  |

  @Negative
  Scenario Outline: Add employee with invalid data
    Given I am on the Add Employee page
    When I enter employee data for "<Scenario>" scenario
    And I click save employee
    Then employee result should be "<Expected>"

    Examples:
      | Scenario              | Expected        |
      | NegativeEmptyFirstName | ValidationError |
      | NegativeEmptyLastName  | ValidationError |
