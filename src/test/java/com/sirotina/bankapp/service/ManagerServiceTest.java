package com.sirotina.bankapp.service;

import com.sirotina.bankapp.dto.ManagerDto;
import com.sirotina.bankapp.entity.Manager;
import com.sirotina.bankapp.mapper.ManagerMapper;
import com.sirotina.bankapp.repository.ManagerRepository;
import com.sirotina.bankapp.service.exception.AccountNotFoundException;
import com.sirotina.bankapp.service.impl.ManagerServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ManagerServiceTest {

    @Mock
    private ManagerRepository repository;

    @Mock
    private ManagerMapper mapper;

    @InjectMocks
    private ManagerServiceImpl managerService;

    @Test
    public void testGetManagerById() {
        UUID id = UUID.randomUUID();
        ManagerDto managerDto = new ManagerDto();
        managerDto.setId(id);

        given(repository.findManagerById(id)).willReturn(Optional.of(new Manager()));
        given(mapper.toDto(any(Manager.class))).willReturn(managerDto);

        ManagerDto result = managerService.getManagerById(id);

        assertThat(result).isEqualTo(managerDto);
    }

    @Test(expected = AccountNotFoundException.class)
    public void testGetManagerByIdNotFound() {
        UUID id = UUID.randomUUID();
        given(repository.findManagerById(id)).willReturn(Optional.empty());

        managerService.getManagerById(id);
    }

    @Test
    public void testGetAllManagers() {
        List<Manager> managers = new ArrayList<>();
        managers.add(new Manager());

        List<ManagerDto> managerDtos = new ArrayList<>();
        managerDtos.add(new ManagerDto());

        given(repository.findAll()).willReturn(managers);
        given(mapper.managersToManagersDto(managers)).willReturn(managerDtos);

        List<ManagerDto> result = managerService.getAllManagers();

        assertThat(result).isEqualTo(managerDtos);
    }
}
