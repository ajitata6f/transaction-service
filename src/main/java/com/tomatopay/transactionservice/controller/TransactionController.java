package com.tomatopay.transactionservice.controller;

import com.tomatopay.transactionservice.dto.request.TransactionRequest;
import com.tomatopay.transactionservice.dto.response.TransactionResponse;
import com.tomatopay.transactionservice.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponse> createTransaction(@Valid @RequestBody TransactionRequest transactionRequest) {
        TransactionResponse transactionResponse = transactionService.createTransaction(transactionRequest);

        return new ResponseEntity<>(transactionResponse, HttpStatus.OK);
    }

    @PutMapping(value = "/{transactionId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponse> updateTransaction(@PathVariable int transactionId, @Valid @RequestBody TransactionRequest transactionRequest) {
        transactionRequest.setId(transactionId);
        TransactionResponse transactionResponse = transactionService.updateTransaction(transactionRequest);

        return new ResponseEntity<>(transactionResponse, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{transactionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponse> deleteTransaction(@PathVariable Integer transactionId) {
        TransactionResponse transactionResponse = transactionService.deleteTransaction(transactionId);

        return new ResponseEntity<>(transactionResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/{transactionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponse> getTransaction(@PathVariable Integer transactionId) {
        TransactionResponse transactionResponse = transactionService.getTransaction(transactionId);

        return new ResponseEntity<>(transactionResponse, HttpStatus.OK);
    }

    @GetMapping(params = {"page", "size"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TransactionResponse>> getTransactions(@RequestParam(value = "page", required = false, defaultValue = "1") int page, @RequestParam(value = "size", required = false, defaultValue = "10") int pageSize) {

        List<TransactionResponse> transactions = transactionService.getTransactions(page, pageSize);

        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

}
