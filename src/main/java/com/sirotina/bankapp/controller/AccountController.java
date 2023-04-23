package com.sirotina.bankapp.controller;

import com.sirotina.bankapp.dto.AccountDTO;
import com.sirotina.bankapp.entity.enums.AccountStatus;
import com.sirotina.bankapp.service.AccountService;
import com.sirotina.bankapp.service.impl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/accounts")
public class AccountController {

    @Autowired
    private AccountServiceImpl service;

    @GetMapping("/{status}")
    public List<AccountDTO> getAllAccountsByStatus(@PathVariable AccountStatus status) {
        return service.findAllAccountsByStatus(status);
    }

    @GetMapping("/list")
    public List<AccountDTO> getAllAccounts() {
        return service.findAllAccounts();
    }

    @PostMapping(value = "/add", consumes = {"application/json"})
    public AccountDTO addNewAccount(@RequestBody AccountDTO accountDTO) {
        System.out.println(accountDTO.toString());
        return service.addNewAccount(accountDTO);
    }

    @PutMapping(path = "edit/{id}", consumes = {"application/json"})
    public AccountDTO editAccountById(@PathVariable UUID id, @RequestBody AccountDTO accountDTO) {
        return service.editAccountById(id, accountDTO);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable UUID id){
        service.deleteById(id);
    }



}
