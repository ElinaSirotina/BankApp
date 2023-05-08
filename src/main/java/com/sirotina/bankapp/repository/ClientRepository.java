package com.sirotina.bankapp.repository;

import com.sirotina.bankapp.entity.Client;
import com.sirotina.bankapp.entity.enums.ClientStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {

    Optional<List<Client>> findAllByStatus(ClientStatus status);

    List<Client> findAll();

    Client findByTaxCode(String taxCode);

    Optional<Client> findClientById(UUID id);

}
