package com.sirotina.bankapp.mapper;

import com.sirotina.bankapp.dto.AccountDTO;
import com.sirotina.bankapp.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountDTO toDto(Account account);

    List<AccountDTO> accountsToAccountsDto(List<Account> accounts);

    Account dtoToAccount(AccountDTO accountDto);

}
