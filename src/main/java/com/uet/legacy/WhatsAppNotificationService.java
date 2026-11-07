package com.uet.legacy;

public class WhatsAppNotificationService
        implements NotificationService {

    @Override
    public void sendNotification(
            String recipient,
            String message) {

        System.out.println(
                "[WHATSAPP DISPATCHED] To: "
                + recipient + " | " + message);
    }
}