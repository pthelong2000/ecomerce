package com.app.repository;

import com.app.emtity.TransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails, Long> {
    List<TransactionDetails> findByOrderId(long orderId);
}
