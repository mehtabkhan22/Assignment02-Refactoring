package com.uet.legacy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NotificationMessageProviderTest {

    @Test
    void testEmailNotificationMessage() {

        NotificationMessageProvider provider =
                new EmailNotificationMessageProvider();

        String message = provider.createMessage(1170.0);

        assertEquals(
                "Your order of PKR 1170.00 is confirmed!",
                message
        );
    }

    @Test
    void testGeneralNotificationMessage() {

        NotificationMessageProvider provider =
                new GeneralNotificationMessageProvider();

        String message = provider.createMessage(1170.0);

        assertEquals(
                "Order confirmed! Paid: PKR 1170.00",
                message
        );
    }
}