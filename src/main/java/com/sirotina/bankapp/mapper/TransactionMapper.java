package com.sirotina.bankapp.mapper;

import com.sirotina.bankapp.dto.TransactionDto;
import com.sirotina.bankapp.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionDto toDTO(Transaction transaction);

    Transaction toEntity(TransactionDto transactionDto);

    List<TransactionDto> transactionsToTransactionsDTO(List<Transaction> transactions);

}
