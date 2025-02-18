# 🛒 Hepsiburada UI Test Automation

This project is a **UI Test Automation Framework** developed to test user scenarios on the **Hepsiburada** website using technologies such as **Selenium, Cucumber, TestNG, and Allure**.

---

## 📌 Project Contents

- **Parallel Browser Support:** Run tests simultaneously on Chrome and Firefox
- **Page Object Model (POM) Usage:** Ensures maintainability of test scenarios
- **Allure Reporting Integration:** Provides detailed analysis of test results
- **Logging System:** Enables detailed log tracking during test execution
- **Cucumber BDD Usage:** Makes scenarios more readable and manageable
- **WebDriverManager Utilization:** Eliminates the need to manually download drivers

---

## 📂 Project Structure

```
📦 Hepsiburada
┣ 📂 src
┃ ┣ 📂 main
┃ ┃ ┣ 📂 java
┃ ┃ ┃ ┣ 📂 factory              # Page Object Factory
┃ ┃ ┃ ┣ 📂 pages                # Page classes
┃ ┃ ┃ ┣ 📂 utils                # Utility tools (Driver, Log, Config, etc.)
┃ ┣ 📂 test
┃ ┃ ┣ 📂 java
┃ ┃ ┃ ┣ 📂 runners              # TestNG Cucumber Runner
┃ ┃ ┃ ┣ 📂 steps                # Step Definitions
┃ ┃ ┃ ┣ 📂 resources
┃ ┃ ┃ ┃ ┣ 📂 features           # Cucumber Feature Files
┃ ┃ ┃ ┃ ┗ 📂 config.properties  # Test environment settings
┣ 📜 pom.xml                    # Maven dependencies
┣ 📜 testng.xml                  # TestNG parallel test execution
┣ 📜 README.md                   # Project documentation
```

---

## 🛠️ Technologies & Tools Used

| Technology | Description |
|------------|------------|
| Java 11+   | Programming language |
| Selenium   | UI automation |
| TestNG     | Test framework |
| Cucumber   | BDD framework |
| WebDriverManager | Driver management |
| Maven      | Dependency management |
| Allure     | Test reporting |
| Logback    | Log management |

---

## 🔧 Setup & Execution

### **1️⃣ Install Dependencies**

```sh
mvn clean install
```

### **2️⃣ Running Tests**

#### **Via Cucumber Feature File**
```sh
mvn test -Dcucumber.features=src/test/resources/features/Tablet.feature
```

#### **Via TestNG XML File (Parallel Execution)**
```sh
mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testng.xml
```

### **3️⃣ Open Allure Report**

```sh
allure serve target/allure-results
```

---

## ✅ Test Scenario

**Scenario: User filters Apple tablets, selects the most expensive product, adds it to the cart, and verifies prices**
```gherkin
Given the user navigates to "https://www.hepsiburada.com/"
When the user navigates to the Tablet category
And the user selects Apple from the Brand filter
When the user accepts all cookies
And the user selects 13.2-inch screen size from the Screen Size filter
And the user selects the most expensive product without sorting
And the user clicks on the add to cart button on the product detail page
Then the product in the cart should have the same price as on the product detail page
```

---

## 🏆 Contributing to the Project

To contribute to this project, follow these steps:
1. **Fork** the repository.
2. Create a new **branch**: `feature/new-feature`
3. Make your changes and **commit**.
4. Submit a **Pull Request (PR)**.

---

🚀 **Happy testing!**

