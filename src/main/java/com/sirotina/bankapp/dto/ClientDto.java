package com.sirotina.bankapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sirotina.bankapp.entity.enums.ClientStatus;
import lombok.Data;
import lombok.Value;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class ClientDto {

    private UUID id;

    private ClientStatus status;

    private String taxCode;

    private String firstName;

    private String lastName;

    private UUID managerId;  //mapped from manager.id

    private String email;

    private String address;

    private String phone;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Timestamp createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Timestamp updatedAt;
}
