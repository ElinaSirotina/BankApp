package com.sirotina.bankapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sirotina.bankapp.dto.TransactionDto;
import com.sirotina.bankapp.service.impl.TransactionServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionServiceImpl transactionService;

    @Test
    public void testGetAll() throws Exception {
        List<TransactionDto> transactions = new ArrayList<>();
        transactions.add(new TransactionDto());

        given(transactionService.getAll()).willReturn(transactions);

        mockMvc.perform(get("/api/transactions/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testGetTransactionById() throws Exception {
        UUID transactionId = UUID.randomUUID();
        TransactionDto transaction = new TransactionDto();
        transaction.setId(transactionId);

        given(transactionService.getTransactionById(transactionId)).willReturn(transaction);

        mockMvc.perform(get("/api/transactions/{id}", transactionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(transactionId.toString())));
    }

//    @Test
//    public void testCreateTransaction() throws Exception {
//        TransactionDto transaction = new TransactionDto();
//        transaction.setAmount(BigDecimal.valueOf(100));
//
//        mockMvc.perform(post("/api/transactions/create")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(transaction)))
//                .andExpect(status().isOk());
//
//        Mockito.verify(transactionService, times(1)).createTransaction(eq(transaction));
//    }

    @Test
    public void testDeleteTransactionById() throws Exception {
        UUID transactionId = UUID.randomUUID();

        mockMvc.perform(delete("/api/transactions/delete/{id}", transactionId))
                .andExpect(status().isOk());

        Mockito.verify(transactionService, times(1)).deleteTransactionById(eq(transactionId));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}