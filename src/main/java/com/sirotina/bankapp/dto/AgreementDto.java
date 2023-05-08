package com.sirotina.bankapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sirotina.bankapp.entity.enums.AgreementStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Data
public class AgreementDto {

    private UUID id;

    private BigDecimal interestRate;

    private AgreementStatus status;

    private BigDecimal sum;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Timestamp createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Timestamp updatedAt;

}
