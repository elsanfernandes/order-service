package com.rummer.os.api.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    private String transactionId;
    private String paymentStatus;
    private int orderId;
    private double amount;
    private String message;
}
