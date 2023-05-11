package com.sirotina.bankapp.controller;

import com.sirotina.bankapp.dto.TransactionDto;
import com.sirotina.bankapp.service.impl.TransactionServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Tag(name="Transaction", description="interaction with transactions")
public class TransactionController {
    private final TransactionServiceImpl transactionService;

    @Operation(
            summary = "Getting all transactions",
            description = "Allows to get a list of all transactions"
    )
    @GetMapping("/transactions")
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionDto> getAll() {
        return transactionService.getAll();
    }

    @Operation(
            summary = "Getting transactions",
            description = "Allows to get transactions by id"
    )
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TransactionDto getTransactionById(@PathVariable UUID id){
        return transactionService.getTransactionById(id);
    }

    @Operation(
            summary = "Creating transactions",
            description = "Allows to create a new transaction"
    )
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public void createTransaction(@RequestBody TransactionDto dto){
        transactionService.createTransaction(dto);
    }

    @Operation(
            summary = "Delete a transaction",
            description = "Allows to delete a transaction by id"
    )
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTransactionById(@PathVariable UUID id){
        transactionService.deleteTransactionById(id);
    }
}
