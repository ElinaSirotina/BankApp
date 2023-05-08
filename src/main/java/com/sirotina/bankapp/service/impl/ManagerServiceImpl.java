package com.sirotina.bankapp.service.impl;

import com.sirotina.bankapp.dto.ManagerDto;
import com.sirotina.bankapp.mapper.ManagerMapper;
import com.sirotina.bankapp.repository.ManagerRepository;
import com.sirotina.bankapp.service.ManagerService;
import com.sirotina.bankapp.service.exception.AccountNotFoundException;
import com.sirotina.bankapp.service.exception.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    private final ManagerMapper mapper;
    private final ManagerRepository repository;

    @Override
    @Transactional(readOnly = true)
    public ManagerDto getManagerById(UUID id) {
        return mapper.toDto(repository.findManagerById(id).orElseThrow(
                () -> new AccountNotFoundException(ErrorMessage.ACCOUNT_NOT_FOUND_BY_ID)));
    }

    @Override
    public List<ManagerDto> getAllManagers() {
        System.out.println(repository.findAll());
        return mapper.managersToManagersDto
                (repository.findAll());
    }

}
