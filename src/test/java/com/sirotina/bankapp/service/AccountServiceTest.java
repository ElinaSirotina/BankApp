package com.sirotina.bankapp.service;

import com.sirotina.bankapp.dto.AccountDto;
import com.sirotina.bankapp.entity.Account;
import com.sirotina.bankapp.entity.enums.AccountStatus;
import com.sirotina.bankapp.mapper.AccountMapper;
import com.sirotina.bankapp.repository.AccountRepository;
import com.sirotina.bankapp.service.exception.AccountExistException;
import com.sirotina.bankapp.service.exception.AccountNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository repository;

    @Mock
    private AccountMapper mapper;

    @InjectMocks
    private AccountService accountService;

    @Test
    public void testFindAllAccountsByStatus() {
        // Arrange
        AccountStatus status = AccountStatus.ACTIVE;
        List<Account> accounts = Arrays.asList(new Account(), new Account());
        when(repository.findAllByStatus(status)).thenReturn(Optional.of(accounts));
        List<AccountDto> accountDtos = Arrays.asList(new AccountDto(), new AccountDto());
        when(mapper.accountsToAccountsDto(accounts)).thenReturn(accountDtos);

        // Act
        List<AccountDto> result = accountService.findAllAccountsByStatus(status);

        // Assert
        assertEquals(accountDtos, result);
    }

    @Test(expected = AccountNotFoundException.class)
    public void testFindAllAccountsByStatusWhenStatusNotFound() {
        // Arrange
        AccountStatus status = AccountStatus.ACTIVE;
        when(repository.findAllByStatus(status)).thenReturn(Optional.empty());

        // Act
        accountService.findAllAccountsByStatus(status);

        // Assert
        // Expects AccountNotFoundException to be thrown
    }

    @Test
    public void testFindAllAccounts() {
        // Arrange
        List<Account> accounts = Arrays.asList(new Account(), new Account());
        when(repository.findAll()).thenReturn(accounts);
        List<AccountDto> accountDtos = Arrays.asList(new AccountDto(), new AccountDto());
        when(mapper.accountsToAccountsDto(accounts)).thenReturn(accountDtos);

        // Act
        List<AccountDto> result = accountService.findAllAccounts();

        // Assert
        assertEquals(accountDtos, result);
    }

    @Test
    public void testAddNewAccount() {
        // Arrange
        AccountDto accountDTO = new AccountDto();
        Account account = new Account();
        when(mapper.dtoToAccount(accountDTO)).thenReturn(account);
        when(repository.findByNickname(account.getNickname())).thenReturn(null);
        Account savedAccount = new Account();
        when(repository.save(account)).thenReturn(savedAccount);
        AccountDto savedAccountDto = new AccountDto();
        when(mapper.toDto(savedAccount)).thenReturn(savedAccountDto);

        // Act
        AccountDto result = accountService.addNewAccount(accountDTO);

        // Assert
        assertEquals(savedAccountDto, result);
        assertNotNull(account.getId());
        assertNotNull(account.getCreatedAt());
        assertNotNull(account.getUpdatedAt());
        verify(repository).findByNickname(account.getNickname());
        verify(repository).save(account);
    }

    @Test(expected = AccountExistException.class)
    public void testAddNewAccountWhenAccountExists() {
        // Arrange
        AccountDto accountDTO = new AccountDto();
        Account account = new Account();
        when(mapper.dtoToAccount(accountDTO)).thenReturn(account);
        when(repository.findByNickname(account.getNickname())).thenReturn(new Account());

        // Act
        accountService.addNewAccount(accountDTO);

        // Assert
        // Expects AccountExistException to be thrown
    }

    @Test
    public void testEditAccountById() {
        // Arrange
        UUID id = UUID.randomUUID();
        AccountDto accountDTO = new AccountDto();
        accountDTO.setNickname("new_nickname");
        accountDTO.setStatus(AccountStatus.BLOCKED);
        Account account = new Account();
        when(repository.findById(id)).thenReturn(Optional.of(account));
        Account savedAccount = new Account();
        when(repository.save(account)).thenReturn(savedAccount);
        AccountDto savedAccountDto = new AccountDto();
        when(mapper.toDto(savedAccount)).thenReturn(savedAccountDto);

        // Act
        AccountDto result = accountService.editAccountById(id, accountDTO);

        // Assert
        assertEquals(savedAccountDto, result);
        assertEquals(accountDTO.getNickname(), account.getNickname());
        assertEquals(AccountStatus.BLOCKED, account.getStatus());
        assertNotNull(account.getUpdatedAt());
        verify(repository).findById(id);
        verify(repository).save(account);
    }

    @Test(expected = AccountNotFoundException.class)
    public void testEditAccountByIdWhenAccountNotFound() {
        // Arrange
        UUID id = UUID.randomUUID();
        AccountDto accountDTO = new AccountDto();
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act
        accountService.editAccountById(id, accountDTO);

        // Assert
        // Expects AccountNotFoundException to be thrown
    }

    @Test
    public void testDeleteById() {
        // Arrange
        UUID id = UUID.randomUUID();
        // Act
        accountService.deleteById(id);
        // Assert
        verify(repository).deleteById(id);
    }
}
