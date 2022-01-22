package com.tomatopay.transactionservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tomatopay.transactionservice.model.Account;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionResponse {

    private String id;
    private String currency;
    private BigDecimal amount;
    private String description;

    private Account account;

    @JsonProperty("type")
    private String transactionType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

}
