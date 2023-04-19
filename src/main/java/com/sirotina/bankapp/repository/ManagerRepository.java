package com.sirotina.bankapp.repository;

import com.sirotina.bankapp.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ManagerRepository extends JpaRepository<Manager, UUID> {
    
    List<Manager> findAll();

    Optional<Manager> findManagerById(UUID id);

}
