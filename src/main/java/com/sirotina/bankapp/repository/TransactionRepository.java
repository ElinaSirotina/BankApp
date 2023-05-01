package com.sirotina.bankapp.repository;

import com.sirotina.bankapp.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    List<Transaction> findAll();

    Optional<Transaction> findTransactionById(UUID id);
}
