# Hepsiburada UI Test Automation

## Overview
This project is an automated UI testing framework for Hepsiburada, one of the largest e-commerce platforms in Turkey. The test automation framework is built using **Selenium WebDriver**, **Cucumber (BDD)**, and **TestNG**, ensuring robust test execution and clear reporting via **Allure Reports**.

## Project Structure
The project follows a structured page object model (POM) with well-defined layers to maintain test scalability and reusability.

```
- factory/                # Page object factory for managing page instances
- pages/                  # Page classes implementing page object model
- runners/                # Test runners for executing Cucumber scenarios
- steps/                  # Step definitions for Cucumber feature files
- utils/                  # Utility classes (driver management, logging, etc.)
- resources/              # Configuration and test resources
    - features/           # Cucumber feature files
    - allure.properties   # Allure report configuration
    - logback.xml         # Logging configuration
    - testng.xml          # TestNG execution configuration
- target/                 # Generated test artifacts
- pom.xml                 # Maven dependencies and build configuration
```

## Technologies Used
- **Java 21**
- **Selenium WebDriver 4.x**
- **Cucumber 7.x**
- **TestNG 7.x**
- **Allure Reports**
- **WebDriverManager**
- **Logback for logging**
- **Maven**

## Installation and Setup
To run the tests, ensure you have the following installed:
- **Java 21**
- **Maven**

### Clone the Repository
```sh
git clone https://github.com/eyupcanbilgin/Hepsiburad-Cucumber-Web.git
cd Hepsiburad-Cucumber-Web
```

### Install Dependencies
```sh
mvn clean install
```

## Running Tests
### Run Tests Using Maven
```sh
mvn test
```

### Run Tests Using TestNG
```sh
mvn test -DsuiteXmlFile=src/test/resources/testng.xml
```

### Run Specific Feature File
```sh
mvn test -Dcucumber.options="src/test/resources/features/Tablet.feature"
```

### Generate Allure Reports
To generate an Allure report after test execution:
```sh
mvn allure:serve
```
This will open the test execution report in a browser.

## Test Scenarios
The project includes automated test cases for Hepsiburada's tablet purchase process, including:
1. Navigating to the **Tablet Category**
2. Filtering by **Brand (Apple)**
3. Filtering by **Screen Size (13.2 inches)**
4. Selecting the **Highest Priced Product**
5. Adding the product to the **Shopping Cart**
6. Verifying the **Cart Price Matches the Product Page Price**

## Parallel Execution
Parallel execution is supported via TestNG, allowing tests to run across multiple browsers simultaneously.
```sh
mvn test -Pparallel
```

## Logging and Debugging
Logging is handled using **Logback**, and logs are generated in the console during execution. For troubleshooting, refer to:
- **target/allure-results/** (Allure reports)
- **Console logs** (Execution status and errors)

## Contribution
If you’d like to contribute:
1. Fork the repository.
2. Create a feature branch (`git checkout -b feature-branch`).
3. Commit your changes (`git commit -m 'Add new feature'`).
4. Push to the branch (`git push origin feature-branch`).
5. Open a Pull Request.

## License
This project is licensed under the **MIT License**.

---

Developed by [Eyupcan Bilgin]

