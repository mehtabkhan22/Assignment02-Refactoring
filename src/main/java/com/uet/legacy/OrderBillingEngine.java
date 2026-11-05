
package com.uet.legacy;

import java.util.List;
import java.util.Map;

public class OrderBillingEngine {

    private final OrderValidator orderValidator = new OrderValidator();

    private final DiscountCalculator discountCalculator
            = new DiscountCalculator();

    private final TaxCalculator taxCalculator = new TaxCalculator();

    private final OrderRepository orderRepository;

    private final Map<String, PaymentStrategy> paymentStrategies
            = new java.util.HashMap<>();

    // Default constructor
    public OrderBillingEngine() {
        this(new FileOrderRepository());
    }

    // Constructor injection
    public OrderBillingEngine(OrderRepository orderRepository) {

        this.orderRepository = orderRepository;

        paymentStrategies.put(
                "CREDIT_CARD", new CreditCardPayment());

        paymentStrategies.put(
                "EASYPAISA", new EasyPaisaPayment());

        paymentStrategies.put(
                "CASH_ON_DELIVERY", new CashOnDeliveryPayment());
    }

    public boolean processOrder(Map<String, Object> orderData) {

        // 1. Input Validation
        if (!orderValidator.validateOrderData(orderData)) {
            return false;
        }

        String email = (String) orderData.get("customerEmail");

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> items
                = (List<Map<String, Object>>) orderData.get("items");

        // 2. Total and Discount Calculation
        double rawTotal = 0.0;

        for (Map<String, Object> item : items) {

            double price = (double) item.get("price");
            int quantity = (int) item.get("quantity");

            rawTotal += price * quantity;
        }

        String customerType = (String)
                orderData.getOrDefault("customerType", "REGULAR");

        double discount = discountCalculator.calculateDiscount(
                rawTotal,
                customerType,
                items.size()
        );

        double netAmount = rawTotal - discount;

        // 3. Tax Calculation
        String country = (String)
                orderData.getOrDefault("country", "PK");

        double tax = taxCalculator.calculateTax(netAmount, country);

        double payableAmount = netAmount + tax;

        // 4. Payment Processing
        String paymentMethod = (String)
                orderData.getOrDefault("paymentMethod", "CASH");

        String paymentDetails = "";

        if (paymentMethod.equals("CREDIT_CARD")) {

            paymentDetails = (String)
                    orderData.getOrDefault("cardNumber", "");

        } else if (paymentMethod.equals("EASYPAISA")) {

            paymentDetails = (String)
                    orderData.getOrDefault("mobileNumber", "");
        }

        PaymentStrategy paymentStrategy =
                paymentStrategies.get(paymentMethod);

        if (paymentStrategy == null) {

            System.out.println(
                    "Error: Unsupported payment method: "
                    + paymentMethod);

            return false;
        }

        if (!paymentStrategy.pay(payableAmount, paymentDetails)) {
            return false;
        }

        String paymentStatus = "PAID";

        if (paymentMethod.equals("CASH_ON_DELIVERY")) {
            paymentStatus = "PENDING_CASH";
        }

        // 5. Save Order Through Repository
        orderRepository.saveOrder(
                email,
                payableAmount,
                paymentStatus
        );

        // 6. Notification Dispatch
        String notifyChannel = (String)
                orderData.getOrDefault("notifyChannel", "EMAIL");

        if (notifyChannel.equals("EMAIL")) {

            NotificationService notificationService
                    = new EmailNotificationService();

            String notificationMessage = "Your order of PKR "
                    + String.format("%.2f", payableAmount)
                    + " is confirmed!";

            notificationService.sendNotification(
                    email, notificationMessage);

        } else if (notifyChannel.equals("SMS")) {

            NotificationService notificationService
                    = new SMSNotificationService();

            String phone = (String)
                    orderData.getOrDefault("phone", "0300-0000000");

            String notificationMessage = "Order confirmed! Paid: PKR "
                    + String.format("%.2f", payableAmount);

            notificationService.sendNotification(
                    phone, notificationMessage);
        }

        return true;
    }

    // Getter and Setter for testing file path redirection
    public void setLogFilePath(String logFilePath) {
        orderRepository.setLogFilePath(logFilePath);
    }

    public String getLogFilePath() {
        return orderRepository.getLogFilePath();
    }
}
