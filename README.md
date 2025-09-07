# HR Payroll Automation Framework

![Java](https://img.shields.io/badge/Java-11+-blue.svg)
![Maven](https://img.shields.io/badge/Maven-3.6+-green.svg)
![Selenium](https://img.shields.io/badge/Selenium-4-orange.svg)
![Cucumber](https://img.shields.io/badge/Cucumber-7-brightgreen.svg)
![TestNG](https://img.shields.io/badge/TestNG-7.8-lightgrey.svg)
![ExtentReports](https://img.shields.io/badge/Reports-ExtentReports-red.svg)

Automation framework for OrangeHRM payroll system using **Java,
Selenium, TestNG, and Cucumber BDD**. Designed to be easy to run,
extend, and maintain.

Test Status - [![HR Payroll Test Automation](https://github.com/ShubhamDubey-GT/HRPayRollAutomation-Capstone/actions/workflows/ci.yml/badge.svg)](https://github.com/ShubhamDubey-GT/HRPayRollAutomation-Capstone/actions/workflows/ci.yml)

## Features

-   Page Object Model for clean code\
-   BDD with Cucumber & Gherkin\
-   Cross-browser support (Chrome, Firefox, Edge)\
-   Parallel execution (local or Grid)\
-   ExtentReports with screenshots\
-   Configurable via `config.properties`\
-   Ready for CI/CD (GitHub Actions, Jenkins)

## Tech Stack

-   Java 11+\
-   Maven\
-   Selenium WebDriver 4\
-   Cucumber 7 + TestNG\
-   ExtentReports 5

## Project Structure

    src/test/java/
     ├── utilities/       # Driver, waits, properties, screenshots
     ├── pages/           # Page objects
     ├── stepDefinitions/ # Step definitions
     ├── testRunners/     # Smoke/Regression runners
     └── hooks/           # Test setup & teardown
    src/test/resources/
     ├── features/        # .feature files
     ├── config/          # config.properties
     ├── extent.properties
     └── testng.xml

## Setup

1.  Install **Java 11+** and **Maven**.\

2.  Clone repo:

    ``` bash
    git clone https://github.com/your-username/hr-payroll-automation.git
    cd hr-payroll-automation
    ```

3.  Install dependencies:

    ``` bash
    mvn clean install -DskipTests
    ```

## Config

Edit `src/test/resources/config/config.properties`:

``` properties
app.url=https://opensource-demo.orangehrmlive.com
browser=chrome
timeout=30
username=Admin
password=admin123
```

## Running Tests

-   Smoke tests:

    ``` bash
    mvn test -Dtest=SmokeTestRunner
    ```

-   Regression suite:

    ``` bash
    mvn test -Dtest=RegressionTestRunner
    ```

-   Different browser:

    ``` bash
    mvn test -Dbrowser=firefox
    ```

-   Headless:

    ``` bash
    mvn test -Dheadless=true
    ```
    
## Reports

-   **Extent Report**: `reports/extent/extent-report.html`\
-   **Cucumber HTML**: `reports/cucumber-html-report/index.html`\
-   **Screenshots**: in `/screenshots/`

## Sample Report

![Extent Report
Screenshot](reports/screenshots/sample-extent-report.png)

## Sample Execution

![Test Execution Screenshot](reports/screenshots/sample-test-run.png)

## Project Layout

![Project Structure](reports/screenshots/project-structure.png)

## CI/CD

-   GitHub Actions workflow: `.github/workflows/ci.yml`\
-   Jenkins pipeline: `Jenkinsfile`\
-   Selenium Grid with Docker supported (`docker-compose.yml`)

## Maintainer

**Shubham Dubey**\
[GitHub Profile](https://github.com/ShubhamDubey-GT)
