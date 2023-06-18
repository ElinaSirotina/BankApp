package com.sirotina.bankapp.mapper;

import com.sirotina.bankapp.dto.ManagerDto;
import com.sirotina.bankapp.entity.Manager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ManagerMapperTest {

    @Autowired
    private ManagerMapper managerMapper;

    @Test
    public void testToDto() {
        Manager manager = new Manager();
        UUID id = UUID.randomUUID();
        manager.setId(id);
        manager.setFirstName("Grigory");

        ManagerDto managerDto = managerMapper.toDto(manager);

        assertNotNull(managerDto);
        assertEquals(manager.getId(), managerDto.getId());
        assertEquals(manager.getFirstName(), managerDto.getFirstName());
    }

    @Test
    public void testManagersToManagersDto() {
        Manager manager1 = new Manager();
        UUID id = UUID.randomUUID();
        manager1.setFirstName("Grigory");

        Manager manager2 = new Manager();
        UUID id2 = UUID.randomUUID();
        manager2.setId(id2);
        manager2.setFirstName("Jane");

        List<Manager> managers = Arrays.asList(manager1, manager2);

        List<ManagerDto> managerDtos = managerMapper.managersToManagersDto(managers);

        assertNotNull(managerDtos);
        assertEquals(managers.size(), managerDtos.size());
        assertEquals(managers.get(0).getId(), managerDtos.get(0).getId());
        assertEquals(managers.get(0).getFirstName(), managerDtos.get(0).getFirstName());
        assertEquals(managers.get(1).getId(), managerDtos.get(1).getId());
        assertEquals(managers.get(1).getFirstName(), managerDtos.get(1).getFirstName());
    }
}
