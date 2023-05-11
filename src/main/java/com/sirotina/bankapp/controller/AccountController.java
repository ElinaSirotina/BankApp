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
            summary = "Getting accounts by status",
            description = "Allows to get a list of accounts by account status"
    )
    @GetMapping("/{status}")
    public List<AccountDto> getAllAccountsByStatus(@PathVariable AccountStatus status) {
        return service.findAllAccountsByStatus(status);
    }

    @Operation(
            summary = "Getting all accounts.",
            description = "Allows to get a list of all accounts"
    )
    @GetMapping("/list")
    public List<AccountDto> getAllAccounts() {
        return service.findAllAccounts();
    }

    @Operation(
            summary = "Add account",
            description = "Allows to add accounts"
    )
    @PostMapping(path = "/add", consumes = {"application/json"})
    public AccountDto addNewAccount(@RequestBody @Parameter(description = "json аккунт") AccountDto accountDTO) {
        System.out.println(accountDTO);
        return service.addNewAccount(accountDTO);
    }

    @Operation(
            summary = "Edit account",
            description = "Allows to edit an account by its id"
    )
    @PutMapping(path = "edit/{id}", consumes = {"application/json"})
    public AccountDto editAccountById(@PathVariable UUID id, @RequestBody AccountDto accountDTO) {
        return service.editAccountById(id, accountDTO);
    }

    @Operation(
            summary = "Delete account",
            description = "Allows to delete an account by its id"
    )
    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable UUID id){
        service.deleteById(id);
    }

}
