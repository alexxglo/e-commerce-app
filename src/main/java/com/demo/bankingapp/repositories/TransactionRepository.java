package com.demo.bankingapp.repositories;

import com.demo.bankingapp.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "SELECT *" +
            " FROM transactions s" +
            " WHERE s.transaction_time >= :startDate" +
            " AND s.transaction_time <= :endDate"
            +" AND (s.receiver_id = :accountId OR s.sender_id = :accountId)", nativeQuery = true)
    List<Transaction> getTransactionByMonthAndYear(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("accountId") Long accountId);
}
