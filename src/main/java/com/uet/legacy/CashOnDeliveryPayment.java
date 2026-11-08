package com.uet.legacy;

public class CashOnDeliveryPayment implements PaymentStrategy {

    @Override
    public boolean pay(double amount, String paymentDetails) {

        if (amount > 25000) {
            System.out.println(
                    "Error: Cash on Delivery is not allowed "
                    + "for orders above PKR 25000!");
            return false;
        }

        System.out.println("Payment Status: PENDING_CASH");

        return true;
    }
}
