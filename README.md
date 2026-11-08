# Assignment 02 – Legacy Code Refactoring

## Software Construction and Development Lab

**Project Name:** Assignment02 – Refactoring  
**Student Name:** Muhammad Mehtab  
**Student ID:** 24018  
**University:** UET Peshawar – Abbottabad Campus  
**Language:** Java  
**Build Tool:** Apache Maven  

---

## 1. Project Overview

This project focuses on refactoring a legacy order billing system to improve its code quality, maintainability, readability, reusability, and testability.

The original order billing engine contained multiple responsibilities in a single class. The system was refactored into smaller, independent classes by applying object-oriented programming principles and suitable software design patterns.

The refactored application supports order validation, payment processing, discount calculation, tax calculation, order storage, and customer notifications.

## 2. Project Objectives

- Identify and improve code smells in the legacy implementation.
- Reduce code complexity and improve readability.
- Apply SOLID principles.
- Separate responsibilities into independent classes.
- Implement suitable design patterns.
- Improve code reusability and maintainability.
- Verify functionality through automated unit testing.
- Use Git for version control and track project changes.

## 3. Technologies Used

- Java
- Apache Maven
- JUnit 5
- NetBeans IDE
- Git
- GitHub

## 4. Refactored Project Architecture

The project is organized into separate classes and packages to improve modularity and separation of concerns.

Important components include:

| Component | Responsibility |
|---|---|
| OrderBillingEngine | Coordinates the order billing process |
| OrderValidator | Validates order information |
| DiscountCalculator | Calculates applicable discounts |
| TaxCalculator | Calculates tax |
| PaymentStrategy | Defines the payment strategy interface |
| CreditCardPayment | Processes credit card payments |
| EasyPaisaPayment | Processes EasyPaisa payments |
| CashOnDeliveryPayment | Handles cash-on-delivery payments |
| NotificationService | Defines notification operations |
| EmailNotificationService | Sends email notifications |
| SMSNotificationService | Sends SMS notifications |
| WhatsAppNotificationService | Handles WhatsApp notifications |
| OrderRepository | Defines order storage operations |
| FileOrderRepository | Implements file-based order storage |

## 5. Design Patterns Applied

### 5.1 Strategy Pattern – Payment Processing

The Strategy Pattern is used to support different payment methods through a common `PaymentStrategy` interface.

Implemented payment strategies:
- CreditCardPayment
- EasyPaisaPayment
- CashOnDeliveryPayment

This allows payment methods to be changed or extended without modifying the main billing logic.

### 5.2 Strategy Pattern – Notification Services

A common `NotificationService` interface is used to support multiple notification channels.

Implemented services:
- EmailNotificationService
- SMSNotificationService
- WhatsAppNotificationService

### 5.3 Provider Abstraction

Notification message and recipient providers separate message generation and recipient selection from notification delivery.

### 5.4 Repository Pattern

The `OrderRepository` interface defines order storage operations, while `FileOrderRepository` provides the file-based implementation.

This separates business logic from data storage.

## 6. Code Quality Improvements

The refactoring process improves the original implementation through:

- Separation of concerns.
- Reduced dependency between components.
- Better code organization.
- Reusable and modular classes.
- Improved maintainability.
- Easier unit testing.
- Clearer responsibilities for individual classes.

A code smell audit document is also included in the project.

## 7. Testing and Validation

JUnit 5 is used for automated unit testing.

The project includes the following test classes:

- `OrderBillingEngineTest`
- `NotificationMessageProviderTest`

### Final Test Results

| Test Class | Tests | Result |
|---|---:|---|
| OrderBillingEngineTest | 43 | Passed |
| NotificationMessageProviderTest | 4 | Passed |
| **Total** | **47** | **All Passed** |

**Final Result:** 47 tests passed, with zero failures, errors, or skipped tests.

The project was successfully built using Maven's `clean install` command.

## 8. How to Run the Project

### Requirements

- Java Development Kit (JDK)
- Apache Maven
- NetBeans IDE or another Java IDE

### Steps

1. Clone or download the repository.
2. Open the project in NetBeans.
3. Allow Maven to load the project dependencies.
4. Open the project terminal.
5. Execute the following command:

```bash
mvn clean install
```

To run the automated tests separately:

```bash
mvn test
```

## 9. Project Structure

```text
Assignment02/
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/uet/legacy/
│   │           ├── Assignment02.java
│   │           ├── OrderBillingEngine.java
│   │           ├── OrderValidator.java
│   │           ├── DiscountCalculator.java
│   │           ├── TaxCalculator.java
│   │           ├── PaymentStrategy.java
│   │           ├── CreditCardPayment.java
│   │           ├── EasyPaisaPayment.java
│   │           ├── CashOnDeliveryPayment.java
│   │           ├── NotificationService.java
│   │           ├── EmailNotificationService.java
│   │           ├── SMSNotificationService.java
│   │           ├── WhatsAppNotificationService.java
│   │           ├── OrderRepository.java
│   │           ├── FileOrderRepository.java
│   │           └── audit/
│   │
│   └── test/
│       └── java/
│           └── com/uet/legacy/
│               ├── OrderBillingEngineTest.java
│               └── NotificationMessageProviderTest.java
│
├── pom.xml
└── README.md
```

## 10. Conclusion

This assignment demonstrates how legacy code can be improved through systematic refactoring, object-oriented design principles, and appropriate design patterns.

The final implementation is more modular, reusable, maintainable, and testable. Successful execution of all 47 automated test cases confirms that the refactored project passes its current test suite.

---

**Repository:**  
https://github.com/mehtabkhan22/Assignment02-Refactoring

