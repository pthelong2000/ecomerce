package com.app.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class PaymentRequest {

    private long orderId;
    private PaymentMode paymentMode;
    private String referenceNumber;
    private long amount;
}

