package com.sirotina.bankapp.service;

import com.sirotina.bankapp.dto.AccountDTO;
import com.sirotina.bankapp.entity.enums.AccountStatus;
import java.util.List;
import java.util.UUID;


public interface AccountService {

    List<AccountDTO> findAllAccountsByStatus(AccountStatus status);

    List<AccountDTO> findAllAccounts();


//    public AccountDTO addNewAccount(AccountDTO accountDTO);

    AccountDTO addNewAccount(AccountDTO accountDTO);

    AccountDTO editAccountById(UUID id, AccountDTO accountDTO);

    void deleteById(UUID id);


}
