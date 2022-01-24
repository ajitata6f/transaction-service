package com.tomatopay.transactionservice.repository;

import com.tomatopay.transactionservice.enums.TransactionType;
import com.tomatopay.transactionservice.exception.TransactionNotFoundException;
import com.tomatopay.transactionservice.model.Account;
import com.tomatopay.transactionservice.model.Transaction;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

@DataJpaTest
@ActiveProfiles(profiles = "test")
public class TransactionRepositoryTest {

    private Transaction transaction;

    @BeforeEach
    void setup() {
        Account account = new Account("erafefdb-f0d9-47d7-bb1g-f4baa8466679");
        transaction = new Transaction( "USD", new BigDecimal("3000.00"), "transaction desc", TransactionType.CREDIT, account);
    }

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void postTransactionTest() {
        transaction = transactionRepository.save(transaction);

        Assert.notNull(transaction.getId());
    }

    @Test
    void updateTransactionTest() {
        transaction = transactionRepository.save(transaction);

        Transaction newTransaction = new Transaction( "USD", new BigDecimal("3000.00"), "transaction desc", TransactionType.CREDIT, transaction.getAccount());

        Transaction updatedTransaction = transactionRepository.save(newTransaction);

        Assert.notNull(updatedTransaction);

        BDDAssertions.assertThat(updatedTransaction.getId()).isEqualTo(newTransaction.getId());
        BDDAssertions.assertThat(updatedTransaction.getAmount()).isEqualTo(newTransaction.getAmount());
        BDDAssertions.assertThat(updatedTransaction.getCurrency()).isEqualTo(newTransaction.getCurrency());
        BDDAssertions.assertThat(updatedTransaction.getTransactionType()).isEqualTo(newTransaction.getTransactionType());
        BDDAssertions.assertThat(updatedTransaction.getAccount()).isEqualTo(newTransaction.getAccount());
    }

    @Test
    void deleteTransactionTest() {
        Account account = new Account("erafefdb-f0d9-47d7-bb1g-f4baa8466679");
        Transaction transaction = new Transaction( "USD", new BigDecimal("3000.00"), "transaction desc", TransactionType.CREDIT, account);

        transactionRepository.save(transaction);

        transactionRepository.delete(transaction);
    }

    @Test
    void getTransactionTest() {
        Account account = new Account("erafefdb-f0d9-47d7-bb1g-f4baa8466679");
        Transaction transaction = new Transaction( "USD", new BigDecimal("3000.00"), "transaction desc", TransactionType.CREDIT, account);

        transaction = transactionRepository.save(transaction);

        Assert.notNull(transaction);

        Transaction foundTransaction = transactionRepository.findById(transaction.getId()).orElseThrow(() -> new TransactionNotFoundException("Transaction does not exist"));

        BDDAssertions.assertThat(foundTransaction.getId()).isEqualTo(transaction.getId());
        BDDAssertions.assertThat(foundTransaction.getAmount()).isEqualTo(transaction.getAmount());
        BDDAssertions.assertThat(foundTransaction.getCurrency()).isEqualTo(transaction.getCurrency());
        BDDAssertions.assertThat(foundTransaction.getTransactionType()).isEqualTo(transaction.getTransactionType());
        BDDAssertions.assertThat(foundTransaction.getAccount()).isEqualTo(transaction.getAccount());
    }


}
