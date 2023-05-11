package com.sirotina.bankapp.service;

import com.sirotina.bankapp.dto.TransactionDto;
import com.sirotina.bankapp.service.exception.AccountNotFoundException;
import com.sirotina.bankapp.entity.Account;
import com.sirotina.bankapp.entity.Transaction;
import com.sirotina.bankapp.mapper.TransactionMapper;
import com.sirotina.bankapp.repository.AccountRepository;
import com.sirotina.bankapp.repository.TransactionRepository;
import com.sirotina.bankapp.service.exception.TransactionNotFoundException;
import com.sirotina.bankapp.service.impl.TransactionServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository repository;

    @Mock
    private TransactionMapper mapper;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    public void testGetAll() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction());

        List<TransactionDto> transactionDtos = new ArrayList<>();
        transactionDtos.add(new TransactionDto());

        given(repository.findAll()).willReturn(transactions);
        given(mapper.transactionsToTransactionsDTO(transactions)).willReturn(transactionDtos);

        List<TransactionDto> result = transactionService.getAll();

        assertThat(result).isEqualTo(transactionDtos);
    }

    @Test
    public void testGetTransactionById() {
        UUID id = UUID.randomUUID();
        Transaction transaction = new Transaction();
        transaction.setId(id);

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(id);

        given(repository.findTransactionById(id)).willReturn(Optional.of(transaction));
        given(mapper.toDTO(transaction)).willReturn(transactionDto);

        TransactionDto result = transactionService.getTransactionById(id);

        assertThat(result).isEqualTo(transactionDto);
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetTransactionByIdNotFound() {
        UUID id = UUID.randomUUID();
        given(repository.findTransactionById(id)).willReturn(Optional.empty());

        transactionService.getTransactionById(id);
    }

    @Test
    public void testCreateTransaction() {
        UUID debitAccountId = UUID.randomUUID();
        Account debitAccount = new Account();
        debitAccount.setId(debitAccountId);
        debitAccount.setBalance(BigDecimal.valueOf(500));

        UUID creditAccountId = UUID.randomUUID();
        Account creditAccount = new Account();
        creditAccount.setId(creditAccountId);
        creditAccount.setBalance(BigDecimal.valueOf(2500));

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setAmount(BigDecimal.valueOf(500));
        transactionDto.setDebitAccountId(debitAccount);
        transactionDto.setCreditAccountId(creditAccount);

        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.valueOf(700));
        transaction.setDebitAccount(debitAccount);
        transaction.setCreditAccount(creditAccount);
        transaction.setCreatedAt(transactionDto.getCreatedAt());

        when(accountRepository.findById(debitAccountId)).thenReturn(Optional.of(debitAccount));
        when(accountRepository.findById(creditAccountId)).thenReturn(Optional.of(creditAccount));
        when(mapper.toEntity(transactionDto)).thenReturn(transaction);
        when(repository.save(transaction)).thenReturn(transaction);

        transactionService.createTransaction(transactionDto);

        assertEquals(debitAccount.getBalance(), BigDecimal.valueOf(500));
        assertEquals(creditAccount.getBalance(), BigDecimal.valueOf(2500));
    }

    @Test
    public void testDeleteTransactionById() {
        UUID id = UUID.randomUUID();
        doNothing().when(repository).deleteById(id);

        transactionService.deleteTransactionById(id);

        verify(repository, times(1)).deleteById(id);
    }
}
