package com.sirotina.bankapp.mapper;

import com.sirotina.bankapp.dto.TransactionDto;
import com.sirotina.bankapp.entity.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionMapperTest {

    @Autowired
    private TransactionMapper transactionMapper;

    @Test
    public void testToDTO() {
        Transaction transaction = new Transaction();
        UUID id = UUID.randomUUID();
        transaction.setId(id);
        transaction.setAmount(BigDecimal.valueOf(100));

        TransactionDto transactionDto = transactionMapper.toDTO(transaction);

        assertNotNull(transactionDto);
        assertEquals(transaction.getId(), transactionDto.getId());
        assertEquals(transaction.getAmount(), transactionDto.getAmount());
    }

    @Test
    public void testToEntity() {
        TransactionDto transactionDto = new TransactionDto();
        UUID id = UUID.randomUUID();
        transactionDto.setId(id);
        transactionDto.setAmount(BigDecimal.valueOf(100));

        Transaction transaction = transactionMapper.toEntity(transactionDto);

        assertNotNull(transaction);
        assertEquals(transactionDto.getId(), transaction.getId());
        assertEquals(transactionDto.getAmount(), transaction.getAmount());
    }

    @Test
    public void testTransactionsToTransactionsDTO() {
        Transaction transaction1 = new Transaction();
        UUID id = UUID.randomUUID();
        transaction1.setId(id);
        transaction1.setAmount(BigDecimal.valueOf(100));

        Transaction transaction2 = new Transaction();
        UUID id2 = UUID.randomUUID();
        transaction2.setId(id2);
        transaction2.setAmount(BigDecimal.valueOf(200));

        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);

        List<TransactionDto> transactionDtos = transactionMapper.transactionsToTransactionsDTO(transactions);

        assertNotNull(transactionDtos);
        assertEquals(transactions.size(), transactionDtos.size());

        assertEquals(transactions.get(0).getId(), transactionDtos.get(0).getId());
        assertEquals(transactions.get(0).getAmount(), transactionDtos.get(0).getAmount());

        assertEquals(transactions.get(1).getId(), transactionDtos.get(1).getId());
        assertEquals(transactions.get(1).getAmount(), transactionDtos.get(1).getAmount());
    }
}
