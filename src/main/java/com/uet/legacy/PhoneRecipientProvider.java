package com.uet.legacy;

import java.util.Map;

public class PhoneRecipientProvider
        implements NotificationRecipientProvider {

    @Override
    public String getRecipient(Map<String, Object> orderData) {

        return (String) orderData.getOrDefault(
                "phone", "0300-0000000");
    }
}