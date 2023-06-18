package com.sirotina.bankapp.mapper;

import com.sirotina.bankapp.dto.AgreementDto;
import com.sirotina.bankapp.entity.Agreement;
import com.sirotina.bankapp.entity.enums.AgreementStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AgreementMapperTest {

    @Autowired
    private AgreementMapper agreementMapper;

    @Test
    public void testAgreementToAgreementDto() {
        Agreement agreement = new Agreement();
        UUID id = UUID.randomUUID();
        agreement.setId(id);
        agreement.setStatus(AgreementStatus.ACTIVE);

        AgreementDto agreementDto = agreementMapper.agreementToAgreementDto(agreement);

        assertNotNull(agreementDto);
        assertEquals(agreement.getId(), agreementDto.getId());
        assertEquals(agreement.getStatus(), agreementDto.getStatus());
    }

    @Test
    public void testAgreementsToAgreementDtos() {
        Agreement agreement1 = new Agreement();
        UUID id = UUID.randomUUID();
        agreement1.setId(id);
        agreement1.setSum(BigDecimal.valueOf(1000));

        Agreement agreement2 = new Agreement();
        agreement2.setId(id);
        agreement2.setSum(BigDecimal.valueOf(1000));

        List<Agreement> agreements = Arrays.asList(agreement1, agreement2);

        List<AgreementDto> agreementDtos = agreementMapper.agreementsToAgreementDtos(agreements);

        assertNotNull(agreementDtos);
        assertEquals(agreements.size(), agreementDtos.size());
        assertEquals(agreements.get(0).getId(), agreementDtos.get(0).getId());
        assertEquals(agreements.get(0).getSum(), agreementDtos.get(0).getStatus());
        assertEquals(agreements.get(1).getId(), agreementDtos.get(1).getId());
        assertEquals(agreements.get(1).getSum(), agreementDtos.get(1).getSum());
    }

    @Test
    public void testAgreementDtoToAgreement() {
        AgreementDto agreementDto = new AgreementDto();
        UUID id = UUID.randomUUID();
        agreementDto.setId(id);
        agreementDto.setSum(BigDecimal.valueOf(1000));

        Agreement agreement = agreementMapper.agreementDtoToAgreement(agreementDto);

        assertNotNull(agreement);
        assertEquals(agreementDto.getId(), agreement.getId());
        assertEquals(agreementDto.getSum(), agreement.getSum());
    }
}
