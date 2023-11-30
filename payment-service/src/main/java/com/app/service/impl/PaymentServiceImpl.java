package com.app.service.impl;

import com.app.emtity.TransactionDetails;
import com.app.modal.PaymentMode;
import com.app.modal.PaymentRequest;
import com.app.modal.PaymentResponse;
import com.app.repository.TransactionDetailsRepository;
import com.app.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final TransactionDetailsRepository repository;

    @Override
    public long doPayment(PaymentRequest request) {
        TransactionDetails transactionDetails = TransactionDetails.builder()
                .paymentDate(Instant.now())
                .orderId(request.getOrderId())
                .paymentMode(request.getPaymentMode().name())
                .referenceNumber(request.getReferenceNumber())
                .amount(request.getAmount())
                .paymentStatus("SUCCESS")
                .build();

        return repository.save(transactionDetails).getId();
    }

    @Override
    public PaymentResponse getTransactionDetailsByOrderId(long orderId) {
        return repository.findByOrderId(orderId)
                .stream().findFirst()
                .map(transactionDetails -> PaymentResponse.builder()
                        .paymentId(transactionDetails.getId())
                        .paymentDate(transactionDetails.getPaymentDate())
                        .status(transactionDetails.getPaymentStatus())
                        .paymentMode(PaymentMode.valueOf(transactionDetails.getPaymentMode()))
                        .amount(transactionDetails.getAmount())
                        .orderId(orderId)
                        .build())
                .orElse(null);
    }
}
