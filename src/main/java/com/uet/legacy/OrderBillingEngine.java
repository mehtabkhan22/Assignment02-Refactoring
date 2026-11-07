
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

    private final Map<String, NotificationService> notificationServices
            = new java.util.HashMap<>();

    private final Map<String, NotificationRecipientProvider>
            recipientProviders = new java.util.HashMap<>();

    // Default constructor
    public OrderBillingEngine() {
        this(new FileOrderRepository());
    }

    // Constructor injection
    public OrderBillingEngine(OrderRepository orderRepository) {

        this.orderRepository = orderRepository;

        // Register Payment Strategies
        registerPaymentStrategy(
                "CREDIT_CARD", new CreditCardPayment());

        registerPaymentStrategy(
                "EASYPAISA", new EasyPaisaPayment());

        registerPaymentStrategy(
                "CASH_ON_DELIVERY", new CashOnDeliveryPayment());

        // Register Notification Services
        registerNotificationService(
                "EMAIL", new EmailNotificationService());

        registerNotificationService(
                "SMS", new SMSNotificationService());

        registerNotificationService(
                "WHATSAPP", new WhatsAppNotificationService());

        // Register Notification Recipient Providers
        registerNotificationRecipientProvider(
                "EMAIL", new EmailRecipientProvider());

        registerNotificationRecipientProvider(
                "SMS", new PhoneRecipientProvider());

        registerNotificationRecipientProvider(
                "WHATSAPP", new PhoneRecipientProvider());
    }

    // Register a new payment strategy
    public void registerPaymentStrategy(
            String paymentMethod,
            PaymentStrategy strategy) {

        if (paymentMethod == null || paymentMethod.isBlank()) {
            throw new IllegalArgumentException(
                    "Payment method cannot be empty!");
        }

        if (strategy == null) {
            throw new IllegalArgumentException(
                    "Payment strategy cannot be null!");
        }

        paymentStrategies.put(paymentMethod, strategy);
    }

    // Register a new notification service
    public void registerNotificationService(
            String channel,
            NotificationService service) {

        if (channel == null || channel.isBlank()) {
            throw new IllegalArgumentException(
                    "Notification channel cannot be empty!");
        }

        if (service == null) {
            throw new IllegalArgumentException(
                    "Notification service cannot be null!");
        }

        notificationServices.put(channel, service);
    }

    // Register a notification recipient provider
    public void registerNotificationRecipientProvider(
            String channel,
            NotificationRecipientProvider provider) {

        if (channel == null || channel.isBlank()) {
            throw new IllegalArgumentException(
                    "Notification channel cannot be empty!");
        }

        if (provider == null) {
            throw new IllegalArgumentException(
                    "Recipient provider cannot be null!");
        }

        recipientProviders.put(channel, provider);
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

        NotificationService notificationService =
                notificationServices.get(notifyChannel);

        NotificationRecipientProvider recipientProvider =
                recipientProviders.get(notifyChannel);

        if (notificationService != null && recipientProvider != null) {

            String recipient =
                    recipientProvider.getRecipient(orderData);

            String notificationMessage;

            if (notifyChannel.equals("EMAIL")) {

                notificationMessage = "Your order of PKR "
                        + String.format("%.2f", payableAmount)
                        + " is confirmed!";

            } else {

                notificationMessage = "Order confirmed! Paid: PKR "
                        + String.format("%.2f", payableAmount);
            }

            notificationService.sendNotification(
                    recipient, notificationMessage);
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
