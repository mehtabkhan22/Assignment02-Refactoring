
package com.uet.legacy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class OrderBillingEngineTest {

  private OrderBillingEngine engine;

    @BeforeEach
    void setUp() {
        engine = new OrderBillingEngine();
        engine.setLogFilePath("test_order_audit.log");
    }

    private Map<String, Object> createOrder(
            String customerType, double price, int quantity) {

        Map<String, Object> item = new HashMap<>();
        item.put("price", price);
        item.put("quantity", quantity);

        List<Map<String, Object>> items = new ArrayList<>();
        items.add(item);

        Map<String, Object> order = new HashMap<>();
        order.put("customerEmail", "test@example.com");
        order.put("items", items);
        order.put("customerType", customerType);
        order.put("country", "PK");
        order.put("paymentMethod", "CASH_ON_DELIVERY");

        return order;
    }

    @Test
    void testRegularCustomerOrder() {
        Map<String, Object> order =
                createOrder("REGULAR", 1000.0, 2);

        assertTrue(engine.processOrder(order));
    }

    @Test
    void testStudentDiscount() {
        Map<String, Object> order =
                createOrder("STUDENT", 1000.0, 2);

        assertTrue(engine.processOrder(order));
    }

    @Test
    void testVipDiscount() {
        Map<String, Object> order =
                createOrder("VIP", 6000.0, 1);

        assertTrue(engine.processOrder(order));
    }

    @Test
    void testCorporateDiscount() {
        Map<String, Object> order =
                createOrder("CORPORATE", 1000.0, 1);

        assertTrue(engine.processOrder(order));
    }
    
@Test
void testVipDiscountAt5000Boundary() {
    Map<String, Object> order =
            createOrder("VIP", 5000.0, 1);

    assertTrue(engine.processOrder(order));
}

@Test
void testRegularDiscountAt10000Boundary() {
    Map<String, Object> order =
            createOrder("REGULAR", 10000.0, 1);

    assertTrue(engine.processOrder(order));
}

@Test
void testRegularDiscountAbove10000() {
    Map<String, Object> order =
            createOrder("REGULAR", 11000.0, 1);

    assertTrue(engine.processOrder(order));
}

@Test
void testCreditCardPayment() {
    Map<String, Object> order =
            createOrder("REGULAR", 1000.0, 1);

    order.put("paymentMethod", "CREDIT_CARD");
    order.put("cardNumber", "1234567890123456");

    assertTrue(engine.processOrder(order));
}

@Test
void testEasyPaisaPayment() {
    Map<String, Object> order =
            createOrder("REGULAR", 1000.0, 1);

    order.put("paymentMethod", "EASYPAISA");
    order.put("mobileNumber", "03001234567");

    assertTrue(engine.processOrder(order));
}

@Test
void testCashOnDeliveryPayment() {
    Map<String, Object> order =
            createOrder("REGULAR", 1000.0, 1);

    order.put("paymentMethod", "CASH_ON_DELIVERY");

    assertTrue(engine.processOrder(order));
}

@Test
void testInvalidCreditCard() {
    Map<String, Object> order =
            createOrder("REGULAR", 1000.0, 1);

    order.put("paymentMethod", "CREDIT_CARD");
    order.put("cardNumber", "12345");

    assertFalse(engine.processOrder(order));
}

@Test
void testInvalidEasyPaisaNumber() {
    Map<String, Object> order =
            createOrder("REGULAR", 1000.0, 1);

    order.put("paymentMethod", "EASYPAISA");
    order.put("mobileNumber", "03123");

    assertFalse(engine.processOrder(order));
}

@Test
void testUnsupportedPaymentMethod() {
    Map<String, Object> order =
            createOrder("REGULAR", 1000.0, 1);

    order.put("paymentMethod", "PAYPAL");

    assertFalse(engine.processOrder(order));
}

@Test
void testEmptyOrderData() {
    Map<String, Object> order = new HashMap<>();

    assertFalse(engine.processOrder(order));
}

@Test
void testInvalidCustomerEmail() {
    Map<String, Object> order =
            createOrder("REGULAR", 1000.0, 1);

    order.put("customerEmail", "invalid-email");

    assertFalse(engine.processOrder(order));
}

@Test
void testEmptyShoppingCart() {
    Map<String, Object> order =
            createOrder("REGULAR", 1000.0, 1);

    order.put("items", new ArrayList<>());

    assertFalse(engine.processOrder(order));
}

@Test
void testNegativeItemPrice() {
    Map<String, Object> order =
            createOrder("REGULAR", -100.0, 1);

    assertFalse(engine.processOrder(order));
}

@Test
void testZeroItemPrice() {
    Map<String, Object> order =
            createOrder("REGULAR", 0.0, 1);

    assertFalse(engine.processOrder(order));
}

@Test
void testNegativeItemQuantity() {
    Map<String, Object> order =
            createOrder("REGULAR", 100.0, -2);

    assertFalse(engine.processOrder(order));
}

@Test
void testCashOnDeliveryAboveLimit() {
    Map<String, Object> order =
            createOrder("REGULAR", 22000.0, 1);

    order.put("paymentMethod", "CASH_ON_DELIVERY");

    assertFalse(engine.processOrder(order));
}

@Test
void testMissingItemPrice() {
    Map<String, Object> item = new HashMap<>();
    item.put("quantity", 2);

    List<Map<String, Object>> items = new ArrayList<>();
    items.add(item);

    Map<String, Object> order = new HashMap<>();
    order.put("customerEmail", "test@example.com");
    order.put("items", items);
    order.put("paymentMethod", "CASH_ON_DELIVERY");

    assertFalse(engine.processOrder(order));
}

@Test
void testMissingItemQuantity() {
    Map<String, Object> item = new HashMap<>();
    item.put("price", 1000.0);

    List<Map<String, Object>> items = new ArrayList<>();
    items.add(item);

    Map<String, Object> order = new HashMap<>();
    order.put("customerEmail", "test@example.com");
    order.put("items", items);
    order.put("paymentMethod", "CASH_ON_DELIVERY");

    assertFalse(engine.processOrder(order));
}

@Test
void testSmsNotification() {
    Map<String, Object> order =
            createOrder("REGULAR", 1000.0, 1);

    order.put("notifyChannel", "SMS");
    order.put("phone", "03001234567");

    assertTrue(engine.processOrder(order));
}
@Test
void testDefaultCashPaymentMethod() {
    Map<String, Object> order =
            createOrder("REGULAR", 1000.0, 1);

    order.put("paymentMethod", "CASH");

    assertFalse(engine.processOrder(order));
}
@Test
void testUSCountryTax() {
    Map<String, Object> order =
            createOrder("REGULAR", 1000.0, 1);

    order.put("country", "US");

    assertTrue(engine.processOrder(order));
}
@Test
void testUKCountryTax() {
    Map<String, Object> order =
            createOrder("REGULAR", 1000.0, 1);

    order.put("country", "UK");

    assertTrue(engine.processOrder(order));
}
@Test
void testDefaultCountryTax() {
    Map<String, Object> order =
            createOrder("REGULAR", 1000.0, 1);

    order.remove("country");

    assertTrue(engine.processOrder(order));
}
@Test
void testUnsupportedNotificationChannel() {
    Map<String, Object> order =
            createOrder("REGULAR", 1000.0, 1);

    order.put("notifyChannel", "WHATSAPP");

    assertTrue(engine.processOrder(order));
}
@Test
void testUnknownCountryUsesDefaultTax() {
    Map<String, Object> order =
            createOrder("REGULAR", 1000.0, 1);

    order.put("country", "XYZ");

    assertTrue(engine.processOrder(order));
}
@Test
void testEmailWithoutDomainExtension() {
    Map<String, Object> order =
            createOrder("REGULAR", 1000.0, 1);

    order.put("customerEmail", "mehtab@gmail");

    assertTrue(engine.processOrder(order));
}
@Test
void testMissingCustomerEmail() {
    Map<String, Object> order =
            createOrder("REGULAR", 1000.0, 1);

    order.remove("customerEmail");

    assertFalse(engine.processOrder(order));
}
@Test
void testZeroItemQuantity() {
    Map<String, Object> order =
            createOrder("REGULAR", 1000.0, 0);

    assertFalse(engine.processOrder(order));
}
@Test
void testMissingPaymentMethod() {
    Map<String, Object> order =
            createOrder("REGULAR", 1000.0, 1);

    order.remove("paymentMethod");

    assertFalse(engine.processOrder(order));
}
@Test
void testMissingCustomerType() {
    Map<String, Object> order =
            createOrder("REGULAR", 1000.0, 1);

    order.remove("customerType");

    assertTrue(engine.processOrder(order));
}
@Test
void testMissingNotificationChannel() {
    Map<String, Object> order =
            createOrder("REGULAR", 1000.0, 1);

    order.remove("notificationChannel");

    assertTrue(engine.processOrder(order));
}
@Test
void testMissingCountry() {
    Map<String, Object> order =
            createOrder("REGULAR", 1000.0, 1);

    order.remove("country");

    assertTrue(engine.processOrder(order));
}
@Test
void testMissingItemsList() {
    Map<String, Object> order =
            createOrder("REGULAR", 1000.0, 1);

    order.remove("items");

    assertFalse(engine.processOrder(order));
}
@Test
void testEmptyCustomerEmail() {
    Map<String, Object> order =
            createOrder("REGULAR", 1000.0, 1);

    order.put("customerEmail", "");

    assertFalse(engine.processOrder(order));
}
@Test
void testWhitespaceOnlyCustomerEmail() {
    Map<String, Object> order =
            createOrder("REGULAR", 1000.0, 1);

    order.put("customerEmail", "   ");

    assertFalse(engine.processOrder(order));
}
@Test
void testUnknownCustomerType() {
    Map<String, Object> order =
            createOrder("GOLD", 1000.0, 1);

    assertTrue(engine.processOrder(order));
}
@Test
void testLowercaseCountryCode() {
    Map<String, Object> order =
            createOrder("REGULAR", 1000.0, 1);

    order.put("country", "pk");

    assertTrue(engine.processOrder(order));
}
@Test
void testNegativePriceAndQuantity() {
    Map<String, Object> order =
            createOrder("REGULAR", -100.0, -2);

    assertFalse(engine.processOrder(order));
}
}
