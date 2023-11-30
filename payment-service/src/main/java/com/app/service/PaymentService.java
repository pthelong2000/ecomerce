package com.app.service;

import com.app.modal.PaymentRequest;
import com.app.modal.PaymentResponse;

public interface PaymentService {
    long doPayment(PaymentRequest request);

    PaymentResponse getTransactionDetailsByOrderId(long orderId);
}
