package com.tomatopay.transactionservice.controller;

import com.tomatopay.transactionservice.dto.request.TransactionCreateRequest;
import com.tomatopay.transactionservice.dto.request.TransactionUpdateRequest;
import com.tomatopay.transactionservice.dto.response.TransactionCreateResponse;
import com.tomatopay.transactionservice.dto.response.TransactionResponse;
import com.tomatopay.transactionservice.dto.response.TransactionUpdateResponse;
import com.tomatopay.transactionservice.model.Transaction;
import com.tomatopay.transactionservice.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionCreateResponse> createTransaction(@Valid @RequestBody TransactionCreateRequest transactionRequest) throws ExecutionException, InterruptedException {
        TransactionCreateResponse transactionCreateResponse = transactionService.createTransaction(transactionRequest);

        return new ResponseEntity<>(transactionCreateResponse, HttpStatus.OK);
    }

    @PutMapping(value = "/{transactionId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionUpdateResponse> updateTransaction(@PathVariable String transactionId, @Valid @RequestBody TransactionUpdateRequest transactionUpdateRequest) throws ExecutionException, InterruptedException {
        transactionUpdateRequest.setId(transactionId);
        TransactionUpdateResponse transactionUpdateResponse = transactionService.updateTransaction(transactionUpdateRequest);

        return new ResponseEntity<>(transactionUpdateResponse, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{transactionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponse> deleteTransaction(@PathVariable String transactionId) {
        TransactionResponse transactionResponse = transactionService.deleteTransaction(transactionId);

        return new ResponseEntity<>(transactionResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/{transactionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Transaction> getTransaction(@PathVariable String transactionId) {
        Transaction transaction = transactionService.getTransaction(transactionId);

        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Transaction>> getTransactions(@RequestParam(value = "page", required = false, defaultValue = "0") int page, @RequestParam(value = "size", required = false, defaultValue = "10") int pageSize) {

        Page<Transaction> transactions = transactionService.getTransactions(page, pageSize);

        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

}
