package com.tomatopay.transactionservice.service.impl;

import com.tomatopay.transactionservice.dto.request.TransactionRequest;
import com.tomatopay.transactionservice.dto.response.TransactionResponse;
import com.tomatopay.transactionservice.service.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Override
    public TransactionResponse createTransaction(TransactionRequest transactionRequest) {
        return null;
    }

    @Override
    public TransactionResponse updateTransaction(TransactionRequest transactionRequest) {
        return null;
    }

    @Override
    public TransactionResponse deleteTransaction(UUID id) {
        return null;
    }

    @Override
    public TransactionResponse getTransaction(UUID id) {
        return null;
    }

    @Override
    public List<TransactionResponse> getTransactions(Integer page, Integer pageSize) {
        return null;
    }

}
