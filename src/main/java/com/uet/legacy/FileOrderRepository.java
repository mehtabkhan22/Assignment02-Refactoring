package com.uet.legacy;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
public class FileOrderRepository implements OrderRepository {
    private String logFilePath = "order_audit.log";

    @Override
    public void saveOrder(
            String email,
            double payableAmount,
            String paymentStatus) {

        try (FileWriter writer = new FileWriter(logFilePath, true)) {

            writer.write(
                    LocalDateTime.now()
                    + " | Order for: " + email
                    + " | Payable: PKR " + payableAmount
                    + " | Status: " + paymentStatus + "\n");

        } catch (IOException e) {

            System.out.println(
                    "Failed to write order log: " + e.getMessage());
        }
    }
    @Override
    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }
    @Override
    public String getLogFilePath() {
        return logFilePath;
    }
}
