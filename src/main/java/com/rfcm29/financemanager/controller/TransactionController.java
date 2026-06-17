package com.rfcm29.financemanager.controller;

import com.rfcm29.financemanager.dto.TransactionRequest;
import com.rfcm29.financemanager.entity.Transaction;
import com.rfcm29.financemanager.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public List<Transaction> getAll(@AuthenticationPrincipal UserDetails user,
                                    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        if (from != null && to != null) {
            return transactionService.getTransactionsByDateRange(user.getUsername(), from, to);
        }
        return transactionService.getTransactions(user.getUsername());
    }

    @PostMapping
    public ResponseEntity<Transaction> create(@Valid @RequestBody TransactionRequest req,
                                              @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(transactionService.createTransaction(req, user.getUsername()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @AuthenticationPrincipal UserDetails user) {
        transactionService.deleteTransaction(id, user.getUsername());
        return ResponseEntity.noContent().build();
    }
}
