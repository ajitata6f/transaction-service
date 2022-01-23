package com.tomatopay.transactionservice.service.impl;

import com.tomatopay.transactionservice.dto.request.TransactionCreateRequest;
import com.tomatopay.transactionservice.dto.request.TransactionUpdateRequest;
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
    public TransactionResponse createTransaction(TransactionCreateRequest transactionRequest) throws ExecutionException, InterruptedException {
        Account account = accountRepository.findById(transactionRequest.getAccountId()).orElseThrow(() -> new AccountNotFoundException("Sorry, account does not exist"));

        Transaction transaction = modelMapper.map(transactionRequest, Transaction.class);

        return processTransaction(account, transaction);
    }

    @Override
    public TransactionResponse updateTransaction(TransactionUpdateRequest transactionRequest) throws ExecutionException, InterruptedException {
        Transaction oldTransaction = transactionRepository.findById(transactionRequest.getId()).orElseThrow(() -> new AccountNotFoundException("Sorry, transaction does not exist"));
        Account account = oldTransaction.getAccount();

        Transaction transaction = modelMapper.map(transactionRequest, Transaction.class);
        transaction.setId(oldTransaction.getId());

        return processTransaction(account, transaction);
    }

    @Override
    public TransactionResponse deleteTransaction(String id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("Sorry, transaction does not exist"));

        transactionRepository.delete(transaction);

        return new TransactionResponse();
    }

    @Override
    public TransactionResponse getTransaction(String id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("Sorry, transaction does not exist"));

        return modelMapper.map(transaction, TransactionResponse.class);
    }

    @Override
    public Page<Transaction> getTransactions(Integer page, Integer pageSize) {
        Pageable paging = PageRequest.of(page, pageSize, Sort.by("id"));

        return transactionRepository.findAll(paging);
    }

    private TransactionResponse processTransaction(Account account, Transaction transaction) throws InterruptedException, ExecutionException {
        transaction.setAccount(account);

        return CompletableFuture.supplyAsync(() -> {
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
        }).get();
    }

}
