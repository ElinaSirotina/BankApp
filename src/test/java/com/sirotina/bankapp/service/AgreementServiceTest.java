package com.sirotina.bankapp.service;

import com.sirotina.bankapp.dto.AgreementDto;
import com.sirotina.bankapp.entity.Agreement;
import com.sirotina.bankapp.entity.enums.AgreementStatus;
import com.sirotina.bankapp.mapper.AgreementMapper;
import com.sirotina.bankapp.repository.AgreementRepository;
import com.sirotina.bankapp.service.exception.AgreementNotFoundException;
import com.sirotina.bankapp.service.impl.AgreementServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AgreementServiceTest {

    @Mock
    private AgreementRepository repository;

    @Mock
    private AgreementMapper mapper;

    @InjectMocks
    private AgreementServiceImpl agreementService;

    @Test
    public void testGetAll() {
        List<Agreement> agreements = new ArrayList<>();
        agreements.add(new Agreement());
        List<AgreementDto> agreementDtos = new ArrayList<>();
        agreementDtos.add(new AgreementDto());

        given(repository.findAll()).willReturn(agreements);
        given(mapper.agreementsToAgreementDtos(agreements)).willReturn(agreementDtos);

        List<AgreementDto> result = agreementService.getAll();

        assertThat(result).isEqualTo(agreementDtos);
    }

    @Test
    public void testGetAgreementsByStatus() {
        List<Agreement> agreements = new ArrayList<>();
        agreements.add(new Agreement());
        List<AgreementDto> agreementDtos = new ArrayList<>();
        agreementDtos.add(new AgreementDto());

        given(repository.findByStatus(any(AgreementStatus.class))).willReturn(agreements);
        given(mapper.agreementsToAgreementDtos(agreements)).willReturn(agreementDtos);

        List<AgreementDto> result = agreementService.getAgreementsByStatus(AgreementStatus.ACTIVE);

        assertThat(result).isEqualTo(agreementDtos);
    }

    @Test
    public void testGetAgreementById() {
        UUID id = UUID.randomUUID();
        Agreement agreement = new Agreement();
        agreement.setId(id);
        AgreementDto agreementDto = new AgreementDto();
        agreementDto.setId(id);

        given(repository.findById(id)).willReturn(Optional.of(agreement));
        given(mapper.agreementToAgreementDto(agreement)).willReturn(agreementDto);

        AgreementDto result = null;
        try {
            result = agreementService.getAgreementById(id);
        } catch (AgreementNotFoundException e) {
            throw new RuntimeException(e);
        }

        assertThat(result).isEqualTo(agreementDto);
    }

    @Test(expected = AgreementNotFoundException.class)
    public void testGetAgreementByIdNotFound() throws AgreementNotFoundException {
        UUID id = UUID.randomUUID();
        given(repository.findById(id)).willReturn(Optional.empty());

        agreementService.getAgreementById(id);
    }

//    @Test
//    public void testCreate() {
//        AgreementDto agreementDto = new AgreementDto();
//        agreementDto.setInterestRate(BigDecimal.ONE);
//        agreementDto.setStatus(AgreementStatus.ACTIVE);
//        agreementDto.setSum(BigDecimal.TEN);
//
//        Agreement agreement = new Agreement();
//        agreement.setId(UUID.randomUUID());
//        agreement.setInterestRate(BigDecimal.ONE);
//        agreement.setStatus(AgreementStatus.ACTIVE);
//        agreement.setSum(BigDecimal.TEN);
//
//        AgreementDto savedAgreementDto = new AgreementDto();
//        savedAgreementDto.setId(agreement.getId());
//        savedAgreementDto.setInterestRate(agreement.getInterestRate());
//        savedAgreementDto.setStatus(agreement.getStatus());
//        savedAgreementDto.setSum(agreement.getSum());
//
//        given(repository.save(any(Agreement.class))).willReturn(agreement);
//        given(mapper.agreementToAgreementDto(agreement)).willReturn(savedAgreementDto);
//
//        AgreementDto result = agreementService.create(agreementDto);
//
//        assertThat(result).isEqualTo(savedAgreementDto);
//        verify(repository, times(1)).save(any(Agreement.class));
//    }

    @Test
    public void testUpdateAgreementById() {
        UUID id = UUID.randomUUID();
        AgreementDto agreementDto = new AgreementDto();
        agreementDto.setInterestRate(BigDecimal.ONE);
        agreementDto.setStatus(AgreementStatus.ACTIVE);
        agreementDto.setSum(BigDecimal.TEN);

        Agreement agreement = new Agreement();
        agreement.setId(id);
        agreement.setInterestRate(BigDecimal.ZERO);
        agreement.setStatus(AgreementStatus.ACTIVE);
        agreement.setSum(BigDecimal.ONE);
        agreement.setUpdatedAt(new Timestamp(new Date().getTime() - 1000000L));

        AgreementDto updatedAgreementDto = new AgreementDto();
        updatedAgreementDto.setId(id);
        updatedAgreementDto.setInterestRate(BigDecimal.ONE);
        updatedAgreementDto.setStatus(AgreementStatus.ACTIVE);
        updatedAgreementDto.setSum(BigDecimal.TEN);

        given(repository.findById(id)).willReturn(Optional.of(agreement));
        given(repository.save(any(Agreement.class))).willReturn(agreement);
        given(mapper.agreementToAgreementDto(agreement)).willReturn(updatedAgreementDto);

        AgreementDto result = null;
        try {
            result = agreementService.updateAgreementById(id, agreementDto);
        } catch (AgreementNotFoundException e) {
            throw new RuntimeException(e);
        }

        assertThat(result).isEqualTo(updatedAgreementDto);
        assertNotNull(agreement.getUpdatedAt());
        verify(repository, times(1)).save(any(Agreement.class));
    }

    @Test(expected = AgreementNotFoundException.class)
    public void testUpdateAgreementByIdNotFound() throws AgreementNotFoundException {
        UUID id = UUID.randomUUID();
        AgreementDto agreementDto = new AgreementDto();
        given(repository.findById(id)).willReturn(Optional.empty());

        agreementService.updateAgreementById(id, agreementDto);
    }

    @Test
    public void testDeleteAgreementById() {
        UUID id = UUID.randomUUID();

        agreementService.deleteAgreementById(id);

        verify(repository, times(1)).deleteById(eq(id));
    }
}
