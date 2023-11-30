package com.app.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class OrderResponse {

    private long orderId;
    private long amount;
    private Instant orderDate;
    private String orderStatus;
    private ProductDetails productDetails;
    private PaymentDetails paymentDetails;

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    @ToString
    public static class ProductDetails {
        private String productName;
        private long productId;
        private long quantity;
        private long price;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    @ToString
    public static class PaymentDetails {
        private long paymentId;
        private String status;
        private PaymentMode paymentMode;
        private Instant paymentDate;
    }
}
