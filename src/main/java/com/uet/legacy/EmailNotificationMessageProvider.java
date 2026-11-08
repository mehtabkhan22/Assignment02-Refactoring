package com.uet.legacy;

public class EmailNotificationMessageProvider
        implements NotificationMessageProvider {

    @Override
    public String createMessage(double payableAmount) {

        return "Your order of PKR "
                + String.format("%.2f", payableAmount)
                + " is confirmed!";
    }
}
