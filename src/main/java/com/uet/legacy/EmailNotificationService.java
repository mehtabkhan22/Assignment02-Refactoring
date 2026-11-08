package com.uet.legacy;

public class EmailNotificationService implements NotificationService {

    @Override
    public void sendNotification(String recipient, String message) {

        System.out.println(
                "[EMAIL DISPATCHED] To: " + recipient
                + " | " + message);
    }
}
