package com.app.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {

    private long productId;
    private long quantity;
    private long totalAmount;
    private PaymentMode paymentMode;
}
