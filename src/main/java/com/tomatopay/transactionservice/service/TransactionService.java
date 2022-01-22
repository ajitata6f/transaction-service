package com.tomatopay.transactionservice.service;

import com.tomatopay.transactionservice.dto.request.TransactionRequest;
import com.tomatopay.transactionservice.dto.response.TransactionResponse;
import com.tomatopay.transactionservice.model.Transaction;
import org.springframework.data.domain.Page;

public interface TransactionService {

    TransactionResponse createTransaction(TransactionRequest transactionRequest);

    TransactionResponse updateTransaction(TransactionRequest transactionRequest);

    TransactionResponse deleteTransaction(Integer id);

    TransactionResponse getTransaction(Integer id);

    Page<Transaction> getTransactions(Integer page, Integer pageSize);

}
