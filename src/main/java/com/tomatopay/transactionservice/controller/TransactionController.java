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
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PutMapping(value = "/{transactionId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponse> updateTransaction(@PathVariable String transactionId, @Valid @RequestBody TransactionRequest transactionRequest) {

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @DeleteMapping(value = "/{transactionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponse> deleteTransaction(@PathVariable String transactionId) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping(value = "/{transactionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponse> getTransaction(@PathVariable String transactionId) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping(params = {"page", "size"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TransactionResponse>> getTransactions(@RequestParam(value = "page", required = false, defaultValue = "1") int page, @RequestParam(value = "size", required = false, defaultValue = "10") int size) {

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
