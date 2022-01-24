package com.tomatopay.transactionservice.service.impl;

import com.tomatopay.transactionservice.dto.request.TransactionCreateRequest;
import com.tomatopay.transactionservice.dto.request.TransactionUpdateRequest;
import com.tomatopay.transactionservice.dto.response.TransactionCreateResponse;
import com.tomatopay.transactionservice.dto.response.TransactionUpdateResponse;
import com.tomatopay.transactionservice.enums.TransactionType;
import com.tomatopay.transactionservice.exception.AccountNotFoundException;
import com.tomatopay.transactionservice.exception.InsufficientFundsException;
import com.tomatopay.transactionservice.model.Account;
import com.tomatopay.transactionservice.model.Transaction;
import com.tomatopay.transactionservice.repository.AccountRepository;
import com.tomatopay.transactionservice.repository.TransactionRepository;
import com.tomatopay.transactionservice.service.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
    public TransactionCreateResponse createTransaction(TransactionCreateRequest transactionRequest) throws ExecutionException, InterruptedException {
        Account account = accountRepository.findById(transactionRequest.getAccountId()).orElseThrow(() -> new AccountNotFoundException("Sorry, account does not exist"));

        Transaction transaction = modelMapper.map(transactionRequest, Transaction.class);
        transaction.setAccount(account);

        return CompletableFuture.supplyAsync(() -> {
            if(TransactionType.CREDIT.equals(transaction.getTransactionType())) {
                BigDecimal newBalance = account.getBalance().add(transaction.getAmount());
                account.setBalance(newBalance);

                accountRepository.save(account);
                return modelMapper.map(transactionRepository.save(transaction), TransactionCreateResponse.class);
            }else {
                if(account.getBalance().compareTo(transaction.getAmount()) > 0) {
                    BigDecimal newBalance = account.getBalance().subtract(transaction.getAmount());
                    account.setBalance(newBalance);

                    accountRepository.save(account);

                    return modelMapper.map(transactionRepository.save(transaction), TransactionCreateResponse.class);
                }else {
                    throw new InsufficientFundsException("Sorry, insufficient funds");
                }
            }
        }).get();
    }

    @Override
    public TransactionUpdateResponse updateTransaction(TransactionUpdateRequest transactionRequest) throws ExecutionException, InterruptedException {
        Transaction oldTransaction = transactionRepository.findById(transactionRequest.getId()).orElseThrow(() -> new AccountNotFoundException("Sorry, transaction does not exist"));
        Account account = oldTransaction.getAccount();

        Transaction transaction = modelMapper.map(transactionRequest, Transaction.class);
        transaction.setId(oldTransaction.getId());

        return CompletableFuture.supplyAsync(() -> {
            if(TransactionType.CREDIT.equals(transaction.getTransactionType())) {
                BigDecimal newBalance = account.getBalance().add(transaction.getAmount());
                account.setBalance(newBalance);

                accountRepository.save(account);
                return modelMapper.map(transactionRepository.save(transaction), TransactionUpdateResponse.class);
            }else {
                if(account.getBalance().compareTo(transaction.getAmount()) > 0) {
                    BigDecimal newBalance = account.getBalance().subtract(transaction.getAmount());
                    account.setBalance(newBalance);

                    accountRepository.save(account);

                    return modelMapper.map(transactionRepository.save(transaction), TransactionUpdateResponse.class);
                }else {
                    throw new InsufficientFundsException("Sorry, insufficient funds");
                }
            }
        }).get();
    }

    @Override
    public void deleteTransaction(String id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("Sorry, transaction does not exist"));

        transactionRepository.delete(transaction);
    }

    @Override
    public Transaction getTransaction(String id) {
        return transactionRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("Sorry, transaction does not exist"));
    }

    @Override
    public Page<Transaction> getTransactions(Integer page, Integer pageSize) {
        Pageable paging = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC,"createdAt"));

        return transactionRepository.findAll(paging);
    }

}
