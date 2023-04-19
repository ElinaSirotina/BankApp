package com.sirotina.bankapp.controller;

import com.sirotina.bankapp.dto.AccountDTO;
import com.sirotina.bankapp.entity.enums.AccountStatus;
import com.sirotina.bankapp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/accounts")
public class AccountController {

    @Autowired
    private AccountService service;

    @GetMapping("/{status}")
    public List<AccountDTO> getAllAccountsByStatus(@PathVariable AccountStatus status) {
        return service.findAllAccountsByStatus(status);
    }

    @GetMapping("/list")
    public List<AccountDTO> getAllManagers() {
        return service.findAllAccounts();
    }

    @PostMapping(value = "/add", consumes = {"application/json"})
    public AccountDTO addNewManager(@RequestBody AccountDTO accountDTO) {
        return service.addNewAccount(accountDTO);
    }

    @PutMapping(path = "edit/{id}", consumes = {"application/json"})
    public AccountDTO editManagerById(@PathVariable UUID id, @RequestBody AccountDTO accountDTO) {
        return service.editAccountById(id, accountDTO);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable UUID id){
        service.deleteById(id);
    }



}
