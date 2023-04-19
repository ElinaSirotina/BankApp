package com.sirotina.bankapp.repository;

import com.sirotina.bankapp.entity.Account;
import com.sirotina.bankapp.entity.enums.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    Optional<List<Account>> findAllByStatus(AccountStatus status);

    List<Account> findAll();

    Account findByNickname(String nickname);

    Optional<Account> findById(UUID id);



}
