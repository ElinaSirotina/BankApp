package com.sirotina.bankapp.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sirotina.bankapp.dto.AccountDto;
import com.sirotina.bankapp.entity.enums.AccountStatus;
import com.sirotina.bankapp.service.impl.AccountServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

    @RunWith(MockitoJUnitRunner.class)
    public class AccountControllerTest {

        private MockMvc mockMvc;

        @Mock
        private AccountServiceImpl accountService;

        @InjectMocks
        private AccountController accountController;

        @Before
        public void setUp() {
            mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
        }

        @Test
        public void testGetAllAccountsByStatus_returnsListOfAccountsByStatus() throws Exception {
            AccountDto account1 = new AccountDto();
            account1.setId(UUID.randomUUID());
            account1.setNickname("John");
            account1.setStatus(AccountStatus.ACTIVE);
            AccountDto account2 = new AccountDto();
            account1.setId(UUID.randomUUID());
            account1.setNickname("Jane");
            account1.setStatus(AccountStatus.BLOCKED);
            List<AccountDto> expectedAccounts = Arrays.asList(account1, account2);
            when(accountService.findAllAccountsByStatus(AccountStatus.ACTIVE)).thenReturn(expectedAccounts);

            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts/{status}", AccountStatus.ACTIVE)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();

            String json = result.getResponse().getContentAsString();
            List<AccountDto> actualAccounts = new ObjectMapper().readValue(json, new TypeReference<>() {});
            assertEquals(expectedAccounts, actualAccounts);
        }

        @Test
        public void testGetAllAccounts_returnsListOfAllAccounts() throws Exception {
            AccountDto account1 = new AccountDto();
            account1.setId(UUID.randomUUID());
            account1.setNickname("John");
            account1.setStatus(AccountStatus.ACTIVE);
            AccountDto account2 = new AccountDto();
            account2.setId(UUID.randomUUID());
            account2.setNickname("Jane");
            account2.setStatus(AccountStatus.BLOCKED);
            List<AccountDto> expectedAccounts = Arrays.asList(account1, account2);
            when(accountService.findAllAccounts()).thenReturn(expectedAccounts);

            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts/list")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();

            String json = result.getResponse().getContentAsString();
            List<AccountDto> actualAccounts = new ObjectMapper().readValue(json, new TypeReference<List<AccountDto>>() {});
            assertEquals(expectedAccounts, actualAccounts);
        }

        @Test
        public void testAddNewAccount_addsNewAccount() throws Exception {
            AccountDto account = new AccountDto();
            account.setId(UUID.randomUUID());
            account.setNickname("John");
            account.setStatus(AccountStatus.ACTIVE);
            String json = new ObjectMapper().writeValueAsString(account);
            when(accountService.addNewAccount(any(AccountDto.class))).thenReturn(account);

            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/accounts/add")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isOk())
                    .andReturn();

            String jsonResponse = result.getResponse().getContentAsString();
            AccountDto actualAccount = new ObjectMapper().readValue(jsonResponse, AccountDto.class);
            assertEquals(account, actualAccount);
        }

        @Test
        public void testEditAccountById_editsExistingAccount() throws Exception {
            UUID accountId = UUID.randomUUID();
            AccountDto updatedAccount = new AccountDto();
            updatedAccount.setId(accountId);
            updatedAccount.setNickname("Jane");
            updatedAccount.setStatus(AccountStatus.BLOCKED);
            String json = new ObjectMapper().writeValueAsString(updatedAccount);
            when(accountService.editAccountById(eq(accountId), any(AccountDto.class))).thenReturn(updatedAccount);

            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/accounts/edit/{id}", accountId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isOk())
                    .andReturn();

            String jsonResponse = result.getResponse().getContentAsString();
            AccountDto actualAccount = new ObjectMapper().readValue(jsonResponse, AccountDto.class);
            assertEquals(updatedAccount, actualAccount);
        }

        @Test
        public void testDelete_deletesAccountById() throws Exception {
            UUID accountId = UUID.randomUUID();

            mockMvc.perform(MockMvcRequestBuilders.delete("/api/accounts/delete/{id}", accountId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

            verify(accountService, times(1)).deleteById(accountId);
        }
    }
