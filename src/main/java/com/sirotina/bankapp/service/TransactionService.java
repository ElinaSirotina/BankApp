package com.sirotina.bankapp.service;

import com.sirotina.bankapp.dto.TransactionDto;
import com.sirotina.bankapp.entity.Transaction;
import com.sirotina.bankapp.service.exception.TransactionNotFoundException;

import java.util.List;
import java.util.UUID;

public interface TransactionService {
    List<TransactionDto> getAll();
    TransactionDto getTransactionById(UUID id);
    void createTransaction(TransactionDto transaction);
    void deleteTransactionById(UUID id) throws TransactionNotFoundException;
}
