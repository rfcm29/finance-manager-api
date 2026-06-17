package com.rfcm29.financemanager.repository;

import com.rfcm29.financemanager.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountIdOrderByDateDesc(Long accountId);

    @Query("SELECT t FROM Transaction t WHERE t.account.user.id = :userId ORDER BY t.date DESC")
    List<Transaction> findAllByUserId(Long userId);

    @Query("SELECT t FROM Transaction t WHERE t.account.user.id = :userId AND t.date BETWEEN :from AND :to ORDER BY t.date DESC")
    List<Transaction> findByUserIdAndDateBetween(Long userId, LocalDate from, LocalDate to);
}
