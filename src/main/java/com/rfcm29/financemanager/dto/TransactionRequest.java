package com.rfcm29.financemanager.dto;

import com.rfcm29.financemanager.entity.Transaction.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionRequest {
    @NotNull  private BigDecimal amount;
    @NotBlank private String description;
    @NotNull  private LocalDate date;
    @NotNull  private TransactionType type;
    @NotNull  private Long accountId;
    private Long categoryId;
    private String notes;
}
