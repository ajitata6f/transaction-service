package com.tomatopay.transactionservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tomatopay.transactionservice.annotations.ValidTransactionType;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

public class TransactionCreateRequest {

    @NotEmpty(message = "Please select transaction account")
    private String accountId;

    @NotEmpty(message = "Please select transaction currency")
    private String currency;

    @DecimalMin(value = "1.00", inclusive = false, message = "please enter a valid amount")
    private BigDecimal amount;

    private String description;

    @JsonProperty("type")
    @ValidTransactionType(regexp = "CREDIT|DEBIT")
    @NotEmpty(message = "Transaction type cannot be empty")
    private String transactionType;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

}
