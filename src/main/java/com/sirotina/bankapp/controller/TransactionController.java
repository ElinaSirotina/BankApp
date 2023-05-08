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
            summary = "Получение всех транзакций",
            description = "Позволяет получить все транзакции"
    )
    @GetMapping("/transactions")
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionDto> getAll() {
        return transactionService.getAll();
    }

    @Operation(
            summary = "Получение транзакции",
            description = "Позволяет получить транзакцию по id"
    )
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TransactionDto getTransactionById(@PathVariable UUID id){
        return transactionService.getTransactionById(id);
    }

    @Operation(
            summary = "Создание транзакции",
            description = "Позволяет создать транзакцию"
    )
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public void createTransaction(@RequestBody TransactionDto dto){
        transactionService.createTransaction(dto);
    }

    @Operation(
            summary = "Удаление транзакции",
            description = "Позволяет удалить транзакцию по id"
    )
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTransactionById(@PathVariable UUID id){
        transactionService.deleteTransactionById(id);
    }
}
