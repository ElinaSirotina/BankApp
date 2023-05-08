package com.sirotina.bankapp.repository;

import com.sirotina.bankapp.entity.Agreement;
import com.sirotina.bankapp.entity.enums.AgreementStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AgreementRepository extends JpaRepository<Agreement, UUID> {

    List<Agreement> findByStatus(AgreementStatus status);

}