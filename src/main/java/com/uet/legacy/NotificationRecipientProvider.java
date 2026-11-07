package com.uet.legacy;

import java.util.Map;

public interface NotificationRecipientProvider {

    String getRecipient(Map<String, Object> orderData);

}