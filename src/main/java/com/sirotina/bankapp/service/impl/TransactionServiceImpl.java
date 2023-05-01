package com.sirotina.bankapp.service.impl;

import com.sirotina.bankapp.dto.TransactionDto;
import com.sirotina.bankapp.entity.Transaction;
import com.sirotina.bankapp.mapper.TransactionMapper;
import com.sirotina.bankapp.repository.AccountRepository;
import com.sirotina.bankapp.repository.TransactionRepository;
import com.sirotina.bankapp.service.TransactionService;
import com.sirotina.bankapp.service.exception.AccountNotFoundException;
import com.sirotina.bankapp.service.exception.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;

    private final AccountRepository accountRepository;

    @Override
    public List<TransactionDto> getAll() {
        for(Transaction dto : transactionRepository.findAll()) {
            System.out.println(dto.toString());
        }
        return transactionMapper.transactionsToTransactionsDTO(transactionRepository.findAll());
    }

    @Override
    public TransactionDto getTransactionById(UUID id) {
        return transactionMapper.toDTO(transactionRepository.findTransactionById(id).get());
    }

    @Override
    public void createTransaction(TransactionDto transactionDto) {
        var debitAccountId = transactionDto.getDebitAccountId().getId();
        var debitAccount = accountRepository.findById(debitAccountId).orElseThrow(
                () -> new AccountNotFoundException(ErrorMessage.ACCOUNT_NOT_FOUND_BY_ID)
        );

        var creditAccountId = transactionDto.getCreditAccountId().getId();
        var creditAccount = accountRepository.findById(creditAccountId).orElseThrow(
                () -> new AccountNotFoundException(ErrorMessage.ACCOUNT_NOT_FOUND_BY_ID)
        );

        Transaction transaction = transactionMapper.toEntity(transactionDto);

        transaction.setCreditAccount(creditAccount);
        transaction.setDebitAccount(debitAccount);

        transactionRepository.save(transaction);
    }

    @Override
    public void deleteTransactionById(UUID id) {
        transactionRepository.deleteById(id);
    }
}
