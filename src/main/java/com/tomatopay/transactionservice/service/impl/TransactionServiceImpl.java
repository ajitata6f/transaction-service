package com.tomatopay.transactionservice.service.impl;

import com.tomatopay.transactionservice.dto.request.TransactionRequest;
import com.tomatopay.transactionservice.dto.response.TransactionResponse;
import com.tomatopay.transactionservice.enums.TransactionType;
import com.tomatopay.transactionservice.exception.AccountNotFoundException;
import com.tomatopay.transactionservice.exception.InsufficientFundsException;
import com.tomatopay.transactionservice.model.Account;
import com.tomatopay.transactionservice.model.Transaction;
import com.tomatopay.transactionservice.repository.AccountRepository;
import com.tomatopay.transactionservice.repository.TransactionRepository;
import com.tomatopay.transactionservice.service.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Transactional
@Service
public class TransactionServiceImpl implements TransactionService {

    private final ModelMapper modelMapper;
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionServiceImpl(ModelMapper modelMapper, TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.modelMapper = modelMapper;
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public TransactionResponse createTransaction(TransactionRequest transactionRequest) {
        Account account = accountRepository.findById(transactionRequest.getAccountId()).orElseThrow(() -> new AccountNotFoundException("Sorry, account does not exist"));

        Transaction transaction = new Transaction();
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setAccount(account);
        transaction.setCurrency(transactionRequest.getCurrency());
        transaction.setDescription(transactionRequest.getDescription());
        transaction.setTransactionType(TransactionType.valueOf(transactionRequest.getTransactionType()));

        if(TransactionType.CREDIT.equals(transaction.getTransactionType())) {
            BigDecimal newBalance = account.getBalance().add(transaction.getAmount());
            account.setBalance(newBalance);

            accountRepository.save(account);
            return modelMapper.map(transactionRepository.save(transaction), TransactionResponse.class);
        }else {
            if(account.getBalance().compareTo(transaction.getAmount()) > 0) {
                BigDecimal newBalance = account.getBalance().subtract(transaction.getAmount());
                account.setBalance(newBalance);

                accountRepository.save(account);
                return modelMapper.map(transactionRepository.save(transaction), TransactionResponse.class);
            }else {
                throw new InsufficientFundsException("Sorry, insufficient funds");
            }
        }
    }

    @Override
    public TransactionResponse updateTransaction(TransactionRequest transactionRequest) {

        return null;
    }

    @Override
    public TransactionResponse deleteTransaction(Integer id) {
        return null;
    }

    @Override
    public TransactionResponse getTransaction(Integer id) {
        return null;
    }

    @Override
    public List<TransactionResponse> getTransactions(Integer page, Integer pageSize) {
        return null;
    }

}
