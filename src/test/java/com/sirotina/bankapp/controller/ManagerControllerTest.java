package com.sirotina.bankapp.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sirotina.bankapp.dto.ManagerDto;
import com.sirotina.bankapp.service.impl.ManagerServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ManagerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ManagerServiceImpl managerService;

    @InjectMocks
    private ManagerController managerController;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(managerController).build();
    }

    @Test
    public void getAllManagers_returnsListOfManagers() throws Exception {
        ManagerDto managerDto = new ManagerDto();
        managerDto.setId(UUID.randomUUID());
        managerDto.setFirstName("John");
        managerDto.setLastName("Doe");
        ManagerDto managerDto2 = new ManagerDto();
        managerDto2.setId(UUID.randomUUID());
        managerDto2.setFirstName("John");
        managerDto2.setLastName("Doe");
        List<ManagerDto> expectedManagers = Arrays.asList(
                managerDto2, managerDto
        );
        when(managerService.getAllManagers()).thenReturn(expectedManagers);
        
        var result = mockMvc.perform(MockMvcRequestBuilders.get("/api/managers/managers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        List<ManagerDto> actualManagers = new ObjectMapper().readValue(json, new TypeReference<List<ManagerDto>>() {});
        assertEquals(expectedManagers, actualManagers);
    }

    @Test
    public void getInfoAboutAccount_returnsManagerById() throws Exception {
        UUID managerId = UUID.randomUUID();
        ManagerDto expectedManager = new ManagerDto();
        expectedManager.setId(managerId);
        expectedManager.setFirstName("John");
        expectedManager.setLastName("Doe");
        when(managerService.getManagerById(managerId)).thenReturn(expectedManager);
        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/managers/managers/{id}", managerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        ManagerDto actualManager = new ObjectMapper().readValue(json, ManagerDto.class);
        assertEquals(expectedManager, actualManager);
    }
}
