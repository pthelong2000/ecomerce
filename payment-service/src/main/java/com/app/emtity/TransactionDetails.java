package com.app.emtity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "transaction_details")
@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "order_id")
    private long orderId;

    @Column(name = "payment_mode")
    private String paymentMode;

    @Column(name = "reference_number")
    private String referenceNumber;

    @Column(name = "payment_date")
    private Instant paymentDate;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "amount")
    private long amount;
}
