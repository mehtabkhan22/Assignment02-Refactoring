
package com.uet.legacy;

public interface OrderRepository {

    void saveOrder(
            String email,
            double payableAmount,
            String paymentStatus
    );

    void setLogFilePath(String logFilePath);

    String getLogFilePath();
}
