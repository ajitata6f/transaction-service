package com.tomatopay.transactionservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomatopay.transactionservice.dto.request.TransactionCreateRequest;
import com.tomatopay.transactionservice.dto.request.TransactionUpdateRequest;
import com.tomatopay.transactionservice.dto.response.TransactionCreateResponse;
import com.tomatopay.transactionservice.dto.response.TransactionUpdateResponse;
import com.tomatopay.transactionservice.enums.TransactionType;
import com.tomatopay.transactionservice.exception.InsufficientFundsException;
import com.tomatopay.transactionservice.model.Account;
import com.tomatopay.transactionservice.model.Transaction;
import com.tomatopay.transactionservice.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    public void postTransactionSuccess() throws Exception {
        TransactionCreateRequest requestDTO = new TransactionCreateRequest();
        requestDTO.setAccountId("d0afecab-f0d9-47d7-bb1f-f4baa8466676");
        requestDTO.setAmount(new BigDecimal("3500.00"));
        requestDTO.setCurrency("NGN");
        requestDTO.setDescription("TomatoPay test payment");
        requestDTO.setTransactionType("CREDIT");

        TransactionCreateResponse responseDTO = new TransactionCreateResponse();
        responseDTO.setId("d0afefdb-f0d9-47d7-bb1g-f4baa8466679");
        responseDTO.setAccount(new Account("d0afecab-f0d9-47d7-bb1f-f4baa8466676"));
        responseDTO.setAmount(new BigDecimal("3500.00"));
        responseDTO.setCurrency("NGN");
        responseDTO.setDescription("TomatoPay test payment");
        responseDTO.setTransactionType("CREDIT");

        //given
        BDDMockito.given(transactionService.createTransaction(BDDMockito.any(TransactionCreateRequest.class))).willReturn(responseDTO);

        //then
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("http://localhost:8080/api/v1/transactions/")
                                .content(objectMapper.writeValueAsString(requestDTO))
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.*", hasSize(6)))
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.account").hasJsonPath())
                .andExpect(jsonPath("$.currency").isString())
                .andExpect(jsonPath("$.amount").isNumber())
                .andExpect(jsonPath("$.type").isString())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }


    @Test
    public void postTransactionInsufficientFunds() throws Exception {
        TransactionCreateRequest requestDTO = new TransactionCreateRequest();
        requestDTO.setAccountId("d0afecab-f0d9-47d7-bb1f-f4baa84666df");
        requestDTO.setAmount(new BigDecimal("100000000000.00"));
        requestDTO.setCurrency("NGN");
        requestDTO.setDescription("TomatoPay test payment");
        requestDTO.setTransactionType("DEBIT");

        //given
        BDDMockito.given(transactionService.createTransaction(BDDMockito.any(TransactionCreateRequest.class))).willThrow(InsufficientFundsException.class);

        //then
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("http://localhost:8080/api/v1/transactions")
                                .content(objectMapper.writeValueAsString(requestDTO))
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

    }

    @Test
    public void updateTransactionSuccess() throws Exception {
        TransactionUpdateRequest requestDTO = new TransactionUpdateRequest();
        requestDTO.setAccountId("d0afecab-f0d9-47d7-bb1f-f4baa8466676");
        requestDTO.setAmount(new BigDecimal("3500.00"));
        requestDTO.setCurrency("NGN");
        requestDTO.setDescription("TomatoPay test payment");
        requestDTO.setTransactionType("CREDIT");

        TransactionUpdateResponse responseDTO = new TransactionUpdateResponse();
        responseDTO.setId("d0afefdb-f0d9-47d7-bb1g-f4baa8466679");
        responseDTO.setAccount(new Account("d0afecab-f0d9-47d7-bb1f-f4baa8466676"));
        responseDTO.setAmount(new BigDecimal("3500.00"));
        responseDTO.setCurrency("NGN");
        responseDTO.setDescription("TomatoPay test payment");
        responseDTO.setTransactionType("CREDIT");

        //given
        BDDMockito.given(transactionService.updateTransaction(BDDMockito.any(TransactionUpdateRequest.class))).willReturn(responseDTO);

        //then
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .put("http://localhost:8080/api/v1/transactions/{id}", "d0afefdb-f0d9-47d7-bb1g-f4baa8466679")
                                .content(objectMapper.writeValueAsString(requestDTO))
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.account").hasJsonPath())
                .andExpect(jsonPath("$.currency").isString())
                .andExpect(jsonPath("$.amount").isNumber())
                .andExpect(jsonPath("$.type").isString())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void deleteTransactionSuccess() throws Exception {
        //given
        BDDMockito.willDoNothing().given(transactionService).deleteTransaction(BDDMockito.anyString());

        //then
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .delete("http://localhost:8080/api/v1/transactions/{id}", "d0afefdb-f0d9-47d7-bb1g-f4baa8466679")
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

    }

    @Test
    public void getTransaction() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setId("d0afefdb-f0d9-47d7-bb1g-f4baa8466679");
        transaction.setAccount(new Account("d0afecab-f0d9-47d7-bb1f-f4baa8466676"));
        transaction.setAmount(new BigDecimal("3500.00"));
        transaction.setCurrency("NGN");
        transaction.setDescription("TomatoPay test payment");
        transaction.setTransactionType(TransactionType.CREDIT);

        //given
        BDDMockito.given(transactionService.getTransaction(BDDMockito.anyString())).willReturn(transaction);

        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("http://localhost:8080/api/v1/transactions/{id}", "d0afefdb-f0d9-47d7-bb1g-f4baa8466679")
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.account").hasJsonPath())
                .andExpect(jsonPath("$.currency").isString())
                .andExpect(jsonPath("$.amount").isNumber())
                .andExpect(jsonPath("$.type").isString())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void getTransactions() throws Exception {
        Account account = new Account("erafefdb-f0d9-47d7-bb1g-f4baa8466679");
        Transaction t1 = new Transaction("d0afefdb-f0d9-47d7-bb1g-f4baa8466679", "USD", new BigDecimal("3000.00"), "transaction desc", TransactionType.CREDIT, account);
        Transaction t2 = new Transaction("d0ghefdb-f0d9-47d7-bb1g-f4baa8466679", "USD", new BigDecimal("3000.00"), "transaction desc", TransactionType.DEBIT, account);
        Transaction t3 = new Transaction("d0ahjfdb-f0d9-47d7-bb1g-f4baa8466679", "USD", new BigDecimal("3000.00"), "transaction desc", TransactionType.DEBIT, account);
        Transaction t4 = new Transaction("d0afasdb-f0d9-47d7-bb1g-f4baa8466679", "USD", new BigDecimal("3000.00"), "transaction desc", TransactionType.CREDIT, account);
        Transaction t5 = new Transaction("d0afjhdb-f0d9-47d7-bb1g-f4baa8466679", "USD", new BigDecimal("3000.00"), "transaction desc", TransactionType.CREDIT, account);

        List<Transaction> transactions = Arrays.asList(t1, t2, t3, t4, t5);
        Page<Transaction> foundPage = new PageImpl<>(transactions);

        //given
        BDDMockito.given(transactionService.getTransactions(Mockito.anyInt(), Mockito.anyInt())).willReturn(foundPage);

        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("http://localhost:8080/api/v1/transactions")
                                .queryParam("page", "0")
                                .queryParam("size", "10")
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.content", hasSize(transactions.size())))
                .andExpect(jsonPath("$.totalPages").isNumber())
                .andExpect(jsonPath("$.totalElements").isNumber())
                .andExpect(jsonPath("$.size").isNumber())
                .andExpect(jsonPath("$.number").isNumber())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

}
