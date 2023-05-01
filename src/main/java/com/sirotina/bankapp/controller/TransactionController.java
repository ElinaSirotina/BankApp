package com.sirotina.bankapp.controller;

import com.sirotina.bankapp.dto.TransactionDto;
import com.sirotina.bankapp.service.impl.TransactionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionServiceImpl transactionService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionDto> getAll() {
        return transactionService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TransactionDto getTransactionById(@PathVariable UUID id){
        return transactionService.getTransactionById(id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public void createTransaction(@RequestBody TransactionDto dto){
        transactionService.createTransaction(dto);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTransactionById(@PathVariable UUID id){
        transactionService.deleteTransactionById(id);
    }
}
