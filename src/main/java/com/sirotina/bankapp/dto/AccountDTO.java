package com.sirotina.bankapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sirotina.bankapp.entity.enums.AccountStatus;
import com.sirotina.bankapp.entity.enums.AccountType;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    private UUID id;

    private String nickname;

    private AccountType type;

    private AccountStatus status;

    @DecimalMin("0.00")
    private Integer balance;

    private String currencyCode;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp updatedAt;

    public AccountDto(UUID id, String nickname, AccountStatus status) {
        this.id = id;
        this.nickname = nickname;
        this.status = status;
    }
}
