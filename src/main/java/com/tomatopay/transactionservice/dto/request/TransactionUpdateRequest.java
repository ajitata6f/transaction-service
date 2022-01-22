package com.tomatopay.transactionservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class TransactionUpdateRequest {
    @NotEmpty(message = "Please select transaction")
    private String id;

    @NotEmpty(message = "Please select transaction account")
    private String accountId;

    @NotEmpty(message = "Please select transaction currency")
    private String currency;

    private BigDecimal amount;

    private String description;

    @JsonProperty("type")
    @NotEmpty(message = "Transaction type cannot be empty")
    private String transactionType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
