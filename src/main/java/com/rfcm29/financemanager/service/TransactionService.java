package com.rfcm29.financemanager.service;

import com.rfcm29.financemanager.dto.TransactionRequest;
import com.rfcm29.financemanager.entity.Transaction;
import com.rfcm29.financemanager.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public List<Transaction> getTransactions(String email) {
        var user = userRepository.findByEmail(email).orElseThrow();
        return transactionRepository.findAllByUserId(user.getId());
    }

    public List<Transaction> getTransactionsByDateRange(String email, LocalDate from, LocalDate to) {
        var user = userRepository.findByEmail(email).orElseThrow();
        return transactionRepository.findByUserIdAndDateBetween(user.getId(), from, to);
    }

    @Transactional
    public Transaction createTransaction(TransactionRequest req, String email) {
        var account = accountRepository.findById(req.getAccountId()).orElseThrow();
        if (!account.getUser().getEmail().equals(email)) throw new SecurityException("Forbidden");

        var tx = Transaction.builder()
                .amount(req.getAmount())
                .description(req.getDescription())
                .date(req.getDate())
                .type(req.getType())
                .account(account)
                .notes(req.getNotes())
                .build();

        if (req.getCategoryId() != null) {
            tx.setCategory(categoryRepository.findById(req.getCategoryId()).orElse(null));
        }

        // update account balance
        switch (req.getType()) {
            case INCOME   -> account.setBalance(account.getBalance().add(req.getAmount()));
            case EXPENSE  -> account.setBalance(account.getBalance().subtract(req.getAmount()));
            default -> {}
        }
        accountRepository.save(account);
        return transactionRepository.save(tx);
    }

    @Transactional
    public void deleteTransaction(Long id, String email) {
        var tx = transactionRepository.findById(id).orElseThrow();
        if (!tx.getAccount().getUser().getEmail().equals(email)) throw new SecurityException("Forbidden");
        // reverse balance
        var account = tx.getAccount();
        switch (tx.getType()) {
            case INCOME  -> account.setBalance(account.getBalance().subtract(tx.getAmount()));
            case EXPENSE -> account.setBalance(account.getBalance().add(tx.getAmount()));
            default -> {}
        }
        accountRepository.save(account);
        transactionRepository.delete(tx);
    }
}
