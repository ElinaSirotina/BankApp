package com.sirotina.bankapp.mapper;

import com.sirotina.bankapp.dto.AccountDto;
import com.sirotina.bankapp.entity.Account;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountDto toDto(Account account);

    List<AccountDto> accountsToAccountsDto(List<Account> accounts);

    Account dtoToAccount(AccountDto accountDto);

}
