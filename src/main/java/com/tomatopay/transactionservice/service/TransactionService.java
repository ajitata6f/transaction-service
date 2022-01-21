package com.tomatopay.transactionservice.service;

import com.tomatopay.transactionservice.dto.request.TransactionRequest;
import com.tomatopay.transactionservice.dto.response.TransactionResponse;

import java.util.List;

public interface TransactionService {

    TransactionResponse createTransaction(TransactionRequest transactionRequest);

    List<TransactionResponse>  getTransactions();



}
