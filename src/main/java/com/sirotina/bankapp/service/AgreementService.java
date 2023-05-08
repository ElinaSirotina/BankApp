package com.sirotina.bankapp.service;

import com.sirotina.bankapp.dto.AgreementDto;
import com.sirotina.bankapp.entity.enums.AgreementStatus;
import com.sirotina.bankapp.service.exception.AgreementNotFoundException;

import java.util.List;
import java.util.UUID;

public interface AgreementService {

    List<AgreementDto> getAll();

    AgreementDto getAgreementById(UUID id) throws AgreementNotFoundException;

    AgreementDto create(AgreementDto dto);

    AgreementDto updateAgreementById(UUID id, AgreementDto dto) throws AgreementNotFoundException;

    void deleteAgreementById(UUID id);

    List<AgreementDto> getAgreementsByStatus(AgreementStatus status);


}
