package com.uet.legacy;

public class EasyPaisaPayment implements PaymentStrategy {

    @Override
    public boolean pay(double amount, String paymentDetails) {

        if (paymentDetails == null
                || paymentDetails.length() != 11
                || !paymentDetails.startsWith("03")) {

            System.out.println("Error: Invalid EasyPaisa mobile number!");
            return false;
        }

        System.out.println(
                "Sending EasyPaisa OTP push to " + paymentDetails
                + " for PKR " + amount);

        return true;
    }
}