package com.uet.legacy;

public class GeneralNotificationMessageProvider
        implements NotificationMessageProvider {

    @Override
    public String createMessage(double payableAmount) {

        return "Order confirmed! Paid: PKR "
                + String.format("%.2f", payableAmount);
    }
}
