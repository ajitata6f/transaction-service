package com.tomatopay.transactionservice.service;

import com.tomatopay.transactionservice.dto.request.TransactionRequest;
import com.tomatopay.transactionservice.dto.response.TransactionResponse;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    TransactionResponse createTransaction(TransactionRequest transactionRequest);

    TransactionResponse updateTransaction(TransactionRequest transactionRequest);

    TransactionResponse deleteTransaction(UUID id);

    TransactionResponse getTransaction(UUID id);

    List<TransactionResponse>  getTransactions(Integer page, Integer pageSize);

}
