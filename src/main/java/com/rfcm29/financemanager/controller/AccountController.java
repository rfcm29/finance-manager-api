package com.rfcm29.financemanager.controller;

import com.rfcm29.financemanager.dto.AccountRequest;
import com.rfcm29.financemanager.entity.Account;
import com.rfcm29.financemanager.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public List<Account> getAccounts(@AuthenticationPrincipal UserDetails user) {
        return accountService.getAccounts(user.getUsername());
    }

    @PostMapping
    public ResponseEntity<Account> create(@Valid @RequestBody AccountRequest req,
                                          @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(accountService.createAccount(req, user.getUsername()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @AuthenticationPrincipal UserDetails user) {
        accountService.deleteAccount(id, user.getUsername());
        return ResponseEntity.noContent().build();
    }
}
