package com.sirotina.bankapp.service.impl;

import com.sirotina.bankapp.dto.AccountDTO;
import com.sirotina.bankapp.entity.Account;
import com.sirotina.bankapp.entity.enums.AccountStatus;
import com.sirotina.bankapp.mapper.AccountMapper;
import com.sirotina.bankapp.repository.AccountRepository;
import com.sirotina.bankapp.service.AccountService;
import com.sirotina.bankapp.service.exception.AccountExistException;
import com.sirotina.bankapp.service.exception.AccountNotFoundException;
import com.sirotina.bankapp.service.exception.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;
    private final AccountMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<AccountDTO> findAllAccountsByStatus(AccountStatus status) {
        return mapper.accountsToAccountsDto(repository.findAllByStatus(status).
                orElseThrow(
                        () -> new AccountNotFoundException
                                ((ErrorMessage.ACCOUNT_NOT_FOUND_BY_STATUS))));
    }

    @Override
    public List<AccountDTO> findAllAccounts() {
        return mapper.accountsToAccountsDto(repository.findAll());
    }

//    @Transactional
//    public AccountDTO addNewAccount(AccountDTO accountDTO) {
//        Account account = mapper.dtoToAccount(accountDTO);
//        if(account.getBalance() == null) {
//            account.setBalance(0);
//        } else {
//            account.setBalance(Integer.parseInt(accountDTO.getBalance()));
//        }
//        checkAccountExist(account.getNickname());
//        account.setId(UUID.randomUUID());
//        account.setCurrencyCode(CurrencyCode.valueOf(accountDTO.getCurrencyCode()));
//        account.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
//        account.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
//
//        repository.save(account);
//        return mapper.toDto(account);
//    }

    @Override
    @Transactional
    public AccountDTO addNewAccount(AccountDTO accountDTO) {
        Account account = mapper.dtoToAccount(accountDTO);
        checkAccountExist(account.getNickname());
        account.setId(UUID.randomUUID());
        account.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        account.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        repository.save(account);
        return mapper.toDto(account);
    }

    private void checkAccountExist(String nickname){
        Account existAccount = repository.findByNickname(nickname);
        if (existAccount != null)
            throw new AccountExistException("The account with the same firstName and lastName already exists");
    }

    @Override
    public AccountDTO editAccountById(UUID id, AccountDTO accountDTO) {
        Account account = repository.findById(id).orElseThrow(
                () -> new AccountNotFoundException(ErrorMessage.ACCOUNT_NOT_FOUND_BY_ID)
        );

        account.setNickname(accountDTO.getNickname());
        account.setStatus(AccountStatus.valueOf(accountDTO.getStatus()));
        account.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        Account result = repository.save(account);
        return mapper.toDto(result);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

}
