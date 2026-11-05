package com.uet.legacy;

public interface PaymentStrategy {

    boolean pay(double amount, String paymentDetails);
}
