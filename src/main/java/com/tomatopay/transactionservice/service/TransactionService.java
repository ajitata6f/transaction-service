package com.tomatopay.transactionservice.service;

import com.tomatopay.transactionservice.dto.request.TransactionCreateRequest;
import com.tomatopay.transactionservice.dto.request.TransactionUpdateRequest;
import com.tomatopay.transactionservice.dto.response.TransactionCreateResponse;
import com.tomatopay.transactionservice.dto.response.TransactionResponse;
import com.tomatopay.transactionservice.dto.response.TransactionUpdateResponse;
import com.tomatopay.transactionservice.model.Transaction;
import org.springframework.data.domain.Page;

import java.util.concurrent.ExecutionException;

public interface TransactionService {

    TransactionCreateResponse createTransaction(TransactionCreateRequest transactionRequest) throws ExecutionException, InterruptedException;

    TransactionUpdateResponse updateTransaction(TransactionUpdateRequest transactionRequest) throws ExecutionException, InterruptedException;

    TransactionResponse deleteTransaction(String id);

    Transaction getTransaction(String id);

    Page<Transaction> getTransactions(Integer page, Integer pageSize);

}
