package com.uet.legacy;

public class SMSNotificationService implements NotificationService {

    @Override
    public void sendNotification(String recipient, String message) {

        System.out.println(
                "[SMS DISPATCHED] To: " + recipient
                + " | " + message);
    }
}