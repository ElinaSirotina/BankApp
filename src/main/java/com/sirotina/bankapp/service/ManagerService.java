package com.sirotina.bankapp.service;

import com.sirotina.bankapp.dto.ManagerDto;

import java.util.List;
import java.util.UUID;

public interface ManagerService {

    ManagerDto getManagerById(UUID id);

    List<ManagerDto> getAllManagers();

}
