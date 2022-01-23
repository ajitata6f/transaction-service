package com.tomatopay.transactionservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomatopay.transactionservice.dto.request.TransactionCreateRequest;
import com.tomatopay.transactionservice.dto.response.TransactionCreateResponse;
import com.tomatopay.transactionservice.model.Account;
import com.tomatopay.transactionservice.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

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
        requestDTO.setAmount(new BigDecimal(3500.00));
        requestDTO.setCurrency("NGN");
        requestDTO.setDescription("TomatoPay test payment");
        requestDTO.setTransactionType("CREDIT");

        TransactionCreateResponse responseDTO = new TransactionCreateResponse();
        responseDTO.setId("d0afefdb-f0d9-47d7-bb1g-f4baa8466679");
        responseDTO.setAccount(new Account("d0afecab-f0d9-47d7-bb1f-f4baa8466676"));
        responseDTO.setAmount(new BigDecimal(3500.00));
        responseDTO.setCurrency("NGN");
        responseDTO.setDescription("TomatoPay test payment");
        responseDTO.setTransactionType("CREDIT");

        //given
        BDDMockito.given(transactionService.createTransaction(BDDMockito.any(TransactionCreateRequest.class))).willReturn(responseDTO);

        ObjectMapper objectMapper = new ObjectMapper();

        MvcResult mvcResult = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("http://localhost:8080/api/v1/transactions/")
                                .content(objectMapper.writeValueAsString(requestDTO))
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                //.andExpect(jsonPath("$.*", hasSize(6)))
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.account").hasJsonPath())
                .andExpect(jsonPath("$.currency").isString())
                .andExpect(jsonPath("$.amount").isNumber())
                .andExpect(jsonPath("$.type").isString())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }


    @Test
    public void postTransactionInsufficientFunds() {

    }

    @Test
    public void updateTransactionSuccess() {

    }

    @Test
    public void updateTransactionInsufficientFunds() {

    }

    @Test
    public void deleteTransactionSuccess() {

    }

    @Test
    public void getTransaction() {

    }

    @Test
    public void getTransactions() {

    }

}
