package com.sirotina.bankapp.service;

import com.sirotina.bankapp.dto.AccountDto;
import com.sirotina.bankapp.entity.enums.AccountStatus;
import java.util.List;
import java.util.UUID;


public interface AccountService {

    List<AccountDto> findAllAccountsByStatus(AccountStatus status);

    List<AccountDto> findAllAccounts();


//    public AccountDTO addNewAccount(AccountDTO accountDTO);

    AccountDto addNewAccount(AccountDto accountDTO);

    AccountDto editAccountById(UUID id, AccountDto accountDTO);

    void deleteById(UUID id);


}
