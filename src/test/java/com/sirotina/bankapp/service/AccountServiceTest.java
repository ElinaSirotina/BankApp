package com.sirotina.bankapp.service;

import com.sirotina.bankapp.dto.AccountDto;
import com.sirotina.bankapp.entity.Account;
import com.sirotina.bankapp.entity.enums.AccountStatus;
import com.sirotina.bankapp.entity.enums.AccountType;
import com.sirotina.bankapp.entity.enums.CurrencyCode;
import com.sirotina.bankapp.mapper.AccountMapper;
import com.sirotina.bankapp.repository.AccountRepository;
import com.sirotina.bankapp.service.exception.AccountExistException;
import com.sirotina.bankapp.service.exception.AccountNotFoundException;
import com.sirotina.bankapp.service.impl.AccountServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository repository;

    @Mock
    private AccountMapper mapper;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    public void testFindAllAccountsByStatus() {
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account());
        List<AccountDto> accountDtos = new ArrayList<>();
        accountDtos.add(new AccountDto());

        given(repository.findAllByStatus(any(AccountStatus.class))).willReturn(Optional.of(accounts));
        given(mapper.accountsToAccountsDto(accounts)).willReturn(accountDtos);

        List<AccountDto> result = accountService.findAllAccountsByStatus(AccountStatus.ACTIVE);

        assertThat(result).isEqualTo(accountDtos);
    }

    @Test(expected = AccountNotFoundException.class)
    public void testFindAllAccountsByStatusNotFound() {
        given(repository.findAllByStatus(any(AccountStatus.class))).willReturn(Optional.empty());

        accountService.findAllAccountsByStatus(AccountStatus.ACTIVE);
    }

    @Test
    public void testFindAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account());
        List<AccountDto> accountDtos = new ArrayList<>();
        accountDtos.add(new AccountDto());

        given(repository.findAll()).willReturn(accounts);
        given(mapper.accountsToAccountsDto(accounts)).willReturn(accountDtos);

        List<AccountDto> result = accountService.findAllAccounts();

        assertThat(result).isEqualTo(accountDtos);
    }

    @Test
    public void testAddNewAccount() {
        String nickname = "test";
        AccountDto accountDto = new AccountDto();
        accountDto.setNickname(nickname);
        accountDto.setType(AccountType.CREDIT);
        accountDto.setStatus(AccountStatus.ACTIVE);
        accountDto.setBalance(BigDecimal.valueOf(2000));
        accountDto.setCurrencyCode("USD");
        accountDto.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        accountDto.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        Account account = new Account();
        account.setId(UUID.randomUUID());
        account.setNickname(nickname);
        account.setType(AccountType.CURRENT);
        account.setStatus(AccountStatus.ACTIVE);
        account.setBalance(BigDecimal.valueOf(2000));
        account.setCurrencyCode(CurrencyCode.USD);
        account.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        account.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        AccountDto savedAccountDto = new AccountDto();
        savedAccountDto.setId(account.getId());
        savedAccountDto.setNickname(account.getNickname());
        savedAccountDto.setType(account.getType());
        savedAccountDto.setStatus(account.getStatus());
        savedAccountDto.setBalance(account.getBalance());
        savedAccountDto.setCurrencyCode(account.getCurrencyCode().toString());
        savedAccountDto.setCreatedAt(account.getCreatedAt());
        savedAccountDto.setUpdatedAt(account.getUpdatedAt());

        given(repository.findByNickname(nickname)).willReturn(null);
        given(repository.save(any(Account.class))).willReturn(account);
        given(mapper.toDto(account)).willReturn(savedAccountDto);

        AccountDto result = accountService.addNewAccount(accountDto);

        assertThat(result).isEqualTo(savedAccountDto);
        verify(repository, times(1)).findByNickname(eq(nickname));
        verify(repository, times(1)).save(any(Account.class));
    }

    @Test(expected = AccountExistException.class)
    public void testAddNewAccountExist() {
        String nickname = "test";
        Account account = new Account();
        account.setId(UUID.randomUUID());
        account.setNickname(nickname);
        AccountDto accountDto = new AccountDto();
        accountDto.setNickname(nickname);
        accountDto.setStatus(AccountStatus.ACTIVE);
        accountDto.setBalance(BigDecimal.valueOf(1000));
        accountDto.setCurrencyCode("USD");

        given(repository.findByNickname(nickname)).willReturn(account);

        accountService.addNewAccount(accountDto);
    }

    @Test
    public void testEditAccountById() {
        UUID id = UUID.randomUUID();
        String nickname = "test";
        AccountDto accountDto = new AccountDto();
        accountDto.setNickname(nickname);
        accountDto.setStatus(AccountStatus.BLOCKED);
        accountDto.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        Account account = new Account();
        account.setId(id);
        account.setNickname("old");
        account.setStatus(AccountStatus.ACTIVE);

        Account updatedAccount = new Account();
        updatedAccount.setId(id);
        updatedAccount.setNickname(nickname);
        updatedAccount.setStatus(AccountStatus.BLOCKED);
        updatedAccount.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        AccountDto updatedAccountDto = new AccountDto();
        updatedAccountDto.setId(id);
        updatedAccountDto.setNickname(nickname);
        updatedAccountDto.setStatus(AccountStatus.BLOCKED);
        updatedAccountDto.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        given(repository.findById(id)).willReturn(Optional.of(account));
        given(repository.save(any(Account.class))).willReturn(updatedAccount);
        given(mapper.toDto(updatedAccount)).willReturn(updatedAccountDto);

        AccountDto result = accountService.editAccountById(id, accountDto);

        assertThat(result).isEqualTo(updatedAccountDto);
        verify(repository, times(1)).findById(eq(id));
        verify(repository, times(1)).save(any(Account.class));
    }

    @Test(expected = AccountNotFoundException.class)
    public void testEditAccountByIdNotFound() {
        UUID id = UUID.randomUUID();
        given(repository.findById(id)).willReturn(Optional.empty());

        accountService.editAccountById(id, new AccountDto());
    }

    @Test
    public void testDeleteById() {
        UUID id = UUID.randomUUID();
        doNothing().when(repository).deleteById(eq(id));

        accountService.deleteById(id);

        verify(repository, times(1)).deleteById(eq(id));
    }

}
