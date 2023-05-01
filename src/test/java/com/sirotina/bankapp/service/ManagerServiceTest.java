package com.sirotina.bankapp.service;

import com.sirotina.bankapp.dto.ManagerDto;
import com.sirotina.bankapp.entity.Manager;
import com.sirotina.bankapp.mapper.ManagerMapper;
import com.sirotina.bankapp.repository.ManagerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ManagerServiceTest {

    @Mock
    private ManagerRepository mockRepository;
    @Mock
    private ManagerMapper mockMapper;

    @InjectMocks
    private ManagerService managerService;

    @Test
    public void testGetManagerById() {
        UUID id = UUID.randomUUID();
        Manager manager = new Manager();
        manager.setId(id);
        ManagerDto managerDto = new ManagerDto();
        managerDto.setId(id);

        when(mockRepository.findManagerById(id)).thenReturn(Optional.of(manager));
        when(mockMapper.toDto(manager)).thenReturn(managerDto);

        ManagerDto result = managerService.getManagerById(id);

        assertEquals(managerDto.getId(), result.getId());
        verify(mockRepository).findManagerById(id);
        verify(mockMapper).toDto(manager);
    }

    @Test
    public void testGetAllManagers() {
        Manager manager1 = new Manager();
        manager1.setId(UUID.randomUUID());
        Manager manager2 = new Manager();
        manager2.setId(UUID.randomUUID());
        List<Manager> managers = Arrays.asList(manager1, manager2);
        ManagerDto managerDto1 = new ManagerDto();
        managerDto1.setId(manager1.getId());
        ManagerDto managerDto2 = new ManagerDto();
        managerDto2.setId(manager2.getId());
        List<ManagerDto> managerDtos = Arrays.asList(managerDto1, managerDto2);

        when(mockRepository.findAll()).thenReturn(managers);
        when(mockMapper.managersToManagersDto(managers)).thenReturn(managerDtos);

        List<ManagerDto> result = managerService.getAllManagers();

        assertEquals(managerDtos.size(), result.size());
        assertEquals(managerDtos.get(0).getId(), result.get(0).getId());
        assertEquals(managerDtos.get(1).getId(), result.get(1).getId());
        verify(mockRepository).findAll();
        verify(mockMapper).managersToManagersDto(managers);
    }
}
