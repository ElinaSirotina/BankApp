package com.sirotina.bankapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sirotina.bankapp.entity.enums.CurrencyCode;
import com.sirotina.bankapp.entity.enums.ProductStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Data
public class ProductDto {

    private UUID id;

    private String name;

    private ProductStatus status;

    private CurrencyCode currencyCode;

    private BigDecimal interestRate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Timestamp createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Timestamp updatedAt;

}
