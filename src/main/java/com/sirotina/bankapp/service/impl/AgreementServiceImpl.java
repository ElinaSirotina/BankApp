package com.sirotina.bankapp.service.impl;

import com.sirotina.bankapp.dto.AgreementDto;
import com.sirotina.bankapp.entity.Agreement;
import com.sirotina.bankapp.entity.enums.AgreementStatus;
import com.sirotina.bankapp.mapper.AgreementMapper;
import com.sirotina.bankapp.repository.AgreementRepository;
import com.sirotina.bankapp.service.AgreementService;
import com.sirotina.bankapp.service.exception.AgreementNotFoundException;
import com.sirotina.bankapp.service.exception.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AgreementServiceImpl implements AgreementService {

    private final AgreementRepository agreementRepository;

    private final AgreementMapper agreementMapper;

    @Override
    public List<AgreementDto> getAll() {
        List<Agreement> agreements = agreementRepository.findAll();
        return agreementMapper.agreementsToAgreementDtos(agreements);
    }

    @Override
    public List<AgreementDto> getAgreementsByStatus(AgreementStatus status) {
        List<Agreement> agreements = agreementRepository.findByStatus(status);
        return agreementMapper.agreementsToAgreementDtos(agreements);
    }

    @Override
    public AgreementDto getAgreementById(UUID id) throws AgreementNotFoundException {
        Optional<Agreement> agreementOptional = agreementRepository.findById(id);
        if (agreementOptional.isPresent()) {
            Agreement agreement = agreementOptional.get();
            return agreementMapper.agreementToAgreementDto(agreement);
        } else {
            throw new AgreementNotFoundException(ErrorMessage.AGREEMENT_NOT_FOUND);
        }
    }

    @Override
    public AgreementDto create(AgreementDto dto) {
        Agreement agreement = agreementMapper.agreementDtoToAgreement(dto);
        Agreement savedAgreement = agreementRepository.save(agreement);
        return agreementMapper.agreementToAgreementDto(savedAgreement);
    }

    @Override
    public AgreementDto updateAgreementById(UUID id, AgreementDto dto) throws AgreementNotFoundException {
        Optional<Agreement> agreementOptional = agreementRepository.findById(id);
        if (agreementOptional.isPresent()) {
            Agreement agreement = agreementOptional.get();
            agreement.setInterestRate(dto.getInterestRate());
            agreement.setStatus(dto.getStatus());
            agreement.setSum(dto.getSum());
            agreement.setUpdatedAt(new Timestamp(new Date().getTime()));
            Agreement updatedAgreement = agreementRepository.save(agreement);
            return agreementMapper.agreementToAgreementDto(updatedAgreement);
        } else {
            throw new AgreementNotFoundException(ErrorMessage.AGREEMENT_NOT_FOUND);
        }
    }

    @Override
    public void deleteAgreementById(UUID id) {
        agreementRepository.deleteById(id);
    }
}
