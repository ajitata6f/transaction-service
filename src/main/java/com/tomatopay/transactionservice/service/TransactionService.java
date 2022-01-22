package com.tomatopay.transactionservice.service;

import com.tomatopay.transactionservice.dto.request.TransactionCreateRequest;
import com.tomatopay.transactionservice.dto.request.TransactionUpdateRequest;
import com.tomatopay.transactionservice.dto.response.TransactionResponse;
import com.tomatopay.transactionservice.model.Transaction;
import org.springframework.data.domain.Page;

import java.util.concurrent.ExecutionException;

public interface TransactionService {

    TransactionResponse createTransaction(TransactionCreateRequest transactionRequest) throws ExecutionException, InterruptedException;

    TransactionResponse updateTransaction(TransactionUpdateRequest transactionRequest);

    TransactionResponse deleteTransaction(Integer id);

    TransactionResponse getTransaction(Integer id);

    Page<Transaction> getTransactions(Integer page, Integer pageSize);

}
