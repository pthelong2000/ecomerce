package com.app.entity;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "[order]")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "product_id")
    private long productId;

    @Column(name = "quantity")
    private long quantity;

    @Column(name = "order_date")
    private Instant orderDate;

    @Column(name = "status")
    private String orderStatus;

    @Column(name = "total_amount")
    private long amount;
}
