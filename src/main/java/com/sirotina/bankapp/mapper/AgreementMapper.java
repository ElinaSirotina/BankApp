package com.sirotina.bankapp.mapper;

import com.sirotina.bankapp.dto.AgreementDto;
import com.sirotina.bankapp.entity.Agreement;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AgreementMapper {

    AgreementDto agreementToAgreementDto(Agreement agreement);

    List<AgreementDto> agreementsToAgreementDtos(List<Agreement> agreements);

    Agreement agreementDtoToAgreement(AgreementDto agreementDto);
}