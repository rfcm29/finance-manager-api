package com.rfcm29.financemanager.dto;

import com.rfcm29.financemanager.entity.Account.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class AccountRequest {
    @NotBlank private String name;
    @NotNull  private AccountType type;
    @NotNull  private BigDecimal initialBalance;
    private String currency = "USD";
}
