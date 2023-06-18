package com.sirotina.bankapp.mapper;

import com.sirotina.bankapp.dto.AccountDto;
import com.sirotina.bankapp.entity.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountMapperTest {

    @Autowired
    private AccountMapper accountMapper;

    @Test
    public void testAccountsToAccountsDto() {
        List<Account> accounts = new ArrayList<>();
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        Account account = new Account();
        account.setId(id1);
        account.setNickname("Bob");
        Account account1 = new Account();
        account1.setId(id2);
        account.setNickname("Vov");

        List<AccountDto> accountDtos = accountMapper.accountsToAccountsDto(accounts);

        assertNotNull(accountDtos);
        assertEquals(accounts.size(), accountDtos.size());
        assertEquals(accounts.get(0).getId(), accountDtos.get(0).getId());
        assertEquals(accounts.get(0).getNickname(), accountDtos.get(0).getNickname());
        assertEquals(accounts.get(1).getId(), accountDtos.get(1).getId());
        assertEquals(accounts.get(1).getNickname(), accountDtos.get(1).getNickname());
    }

    @Test
    public void testDtoToAccount() {
        AccountDto accountDto = new AccountDto();
        UUID id = UUID.randomUUID();
        accountDto.setId(id);
        accountDto.setNickname("Bob");

        Account account = accountMapper.dtoToAccount(accountDto);

        assertNotNull(account);
        assertEquals(accountDto.getId(), account.getId());
        assertEquals(accountDto.getNickname(), account.getNickname());
    }

    @Test
    public void testToDto() {
        Account account = new Account();
        UUID id = UUID.randomUUID();
        account.setId(id);
        account.setNickname("Bob");

        AccountDto accountDto = accountMapper.toDto(account);

        assertNotNull(accountDto);
        assertEquals(account.getId(), accountDto.getId());
        assertEquals(account.getNickname(), accountDto.getNickname());
    }
}
