package com.sirotina.bankapp.controller;

import com.sirotina.bankapp.dto.AccountDto;
import com.sirotina.bankapp.entity.enums.AccountStatus;
import com.sirotina.bankapp.service.impl.AccountServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/accounts")
@RequiredArgsConstructor
@Tag(name="Accounts", description="interaction with accounts")
public class AccountController {

    private final AccountServiceImpl service;

    @Operation(
            summary = "Получение аккаунтов по статусу",
            description = "Позволяет получить список аккаунтов по статусу аккаунта"
    )
    @GetMapping("/{status}")
    public List<AccountDto> getAllAccountsByStatus(@PathVariable AccountStatus status) {
        return service.findAllAccountsByStatus(status);
    }

    @Operation(
            summary = "Получение всех аккаунтов",
            description = "Позволяет получить список всех аккаунтов"
    )
    @GetMapping("/list")
    public List<AccountDto> getAllAccounts() {
        return service.findAllAccounts();
    }

    @Operation(
            summary = "Добавить аккаунт",
            description = "Позволяет добавить аккаунт"
    )
    @PostMapping(path = "/add", consumes = {"application/json"})
    public AccountDto addNewAccount(@RequestBody @Parameter(description = "json аккунт") AccountDto accountDTO) {
        System.out.println(accountDTO);
        return service.addNewAccount(accountDTO);
    }

    @Operation(
            summary = "Редактировать аккаунт",
            description = "Позволяет редактировать аккаунт по его id"
    )
    @PutMapping(path = "edit/{id}", consumes = {"application/json"})
    public AccountDto editAccountById(@PathVariable UUID id, @RequestBody AccountDto accountDTO) {
        return service.editAccountById(id, accountDTO);
    }

    @Operation(
            summary = "Удалить аккаунт",
            description = "Позволяет удалить аккаунт по его id"
    )
    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable UUID id){
        service.deleteById(id);
    }

}
