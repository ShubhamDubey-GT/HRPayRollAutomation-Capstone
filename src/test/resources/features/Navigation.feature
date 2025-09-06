@Navigation @Smoke
Feature: HR Payroll System Navigation - Data Driven

  Background:
    Given I am logged in as admin user

  @Positive @Critical
  Scenario Outline: Navigate to different modules
    When I navigate to "<Module>" module
    Then navigation result should be "<Expected>"

    Examples:
      | Module | Expected           |
      | Admin  | AdminPageDisplayed |
      | PIM    | PIMPageDisplayed   |
      | Leave  | LeavePageDisplayed |
