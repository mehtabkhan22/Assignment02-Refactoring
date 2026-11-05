
# Code Smell and SOLID Audit

## 1. Code Smells

### 1. God Class
**Location:** OrderBillingEngine.java, Lines 10–229

**Problem:** The class performs multiple responsibilities, including validation, discount calculation, tax calculation, payment processing, file logging, and notifications.

**Proposed Solution:** Divide responsibilities into separate classes.

### 2. Long Method
**Location:** OrderBillingEngine.java, Lines 14–220

**Problem:** The processOrder() method contains many different operations, making it difficult to understand, test, and maintain.

**Proposed Solution:** Extract smaller methods and delegate tasks to separate classes.

### 3. Magic Numbers
**Location:** OrderBillingEngine.java, Lines 71–112 and 126–167

**Problem:** Values such as 5000, 10000, 0.17, and 25000 are directly embedded in the code.

**Proposed Solution:** Replace unexplained numeric values with meaningful named constants.

### 4. Primitive Obsession
**Location:** OrderBillingEngine.java, Lines 14–18, 65–67, 99–100, and 116–118

**Problem:** Generic maps, strings, and primitive values represent concepts such as orders, customers, countries, and payment methods.

**Proposed Solution:** Introduce meaningful domain classes and enums where appropriate.

### 5. Direct File I/O
**Location:** OrderBillingEngine.java, Lines 181–195

**Problem:** The billing class directly handles file writing using FileWriter.

**Proposed Solution:** Move persistence operations into a separate class behind an OrderRepository interface.

### 6. Long Conditional Chains
**Location:** OrderBillingEngine.java, Lines 121–179 and 201–218

**Problem:** Long if-else chains handle payment methods and notification channels.

**Proposed Solution:** Use the Strategy Pattern for payment methods and an interface for notification services.

## 2. SOLID Principle Violations

### 1. Single Responsibility Principle (SRP)
**Location:** OrderBillingEngine.java, Lines 14–220

**Problem:** One class handles validation, billing, payments, logging, and notifications.

**Proposed Solution:** Separate these responsibilities into focused classes.

### 2. Open/Closed Principle (OCP)
**Location:** OrderBillingEngine.java, Lines 121–179 and 201–218

**Problem:** Adding new payment methods or notification channels requires modifying existing conditional logic.

**Proposed Solution:** Use PaymentStrategy and NotificationService interfaces with separate implementations.

### 3. Dependency Inversion Principle (DIP)
**Location:** OrderBillingEngine.java, Lines 181–195

**Problem:** High-level order processing directly depends on the concrete FileWriter implementation.

**Proposed Solution:** Depend on an OrderRepository interface instead of directly using FileWriter.

