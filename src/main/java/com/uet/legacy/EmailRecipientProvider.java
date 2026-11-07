package com.uet.legacy;

import java.util.Map;

public class EmailRecipientProvider
        implements NotificationRecipientProvider {

    @Override
    public String getRecipient(Map<String, Object> orderData) {

        return (String) orderData.get("customerEmail");
    }
}