package com.app.controller;

import com.app.modal.PaymentRequest;
import com.app.modal.PaymentResponse;
import com.app.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest request) {
        return ResponseEntity.ok(paymentService.doPayment(request));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponse> getTransactionDetailByOrderId(@PathVariable("orderId") long orderId) {
        return ResponseEntity.ok(paymentService.getTransactionDetailsByOrderId(orderId));
    }
}
