package com.rfcm29.financemanager.service;

import com.rfcm29.financemanager.dto.AccountRequest;
import com.rfcm29.financemanager.entity.Account;
import com.rfcm29.financemanager.repository.AccountRepository;
import com.rfcm29.financemanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public List<Account> getAccounts(String email) {
        var user = userRepository.findByEmail(email).orElseThrow();
        return accountRepository.findByUserId(user.getId());
    }

    public Account createAccount(AccountRequest req, String email) {
        var user = userRepository.findByEmail(email).orElseThrow();
        var account = Account.builder()
                .name(req.getName())
                .type(req.getType())
                .balance(req.getInitialBalance())
                .currency(req.getCurrency())
                .user(user)
                .build();
        return accountRepository.save(account);
    }

    public void deleteAccount(Long id, String email) {
        var account = accountRepository.findById(id).orElseThrow();
        if (!account.getUser().getEmail().equals(email)) throw new SecurityException("Forbidden");
        accountRepository.delete(account);
    }
}
