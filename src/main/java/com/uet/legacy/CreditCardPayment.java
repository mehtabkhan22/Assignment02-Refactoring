package com.uet.legacy;

public class CreditCardPayment implements PaymentStrategy {

    @Override
    public boolean pay(double amount, String paymentDetails) {

        if (paymentDetails == null
                || !paymentDetails.matches("\\d{16}")) {

            System.out.println("Error: Invalid credit card number!");
            return false;
        }

        System.out.println(
                "Processing credit card payment of PKR " + amount);

        return true;
    }
}
