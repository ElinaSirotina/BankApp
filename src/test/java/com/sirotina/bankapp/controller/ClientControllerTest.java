package com.sirotina.bankapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sirotina.bankapp.dto.ClientDto;
import com.sirotina.bankapp.entity.enums.ClientStatus;
import com.sirotina.bankapp.service.ClientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ClientController.class)
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @Test
    public void testGetAllClientsByStatus() throws Exception {
        List<ClientDto> clients = new ArrayList<>();
        clients.add(new ClientDto());

        given(clientService.getAllClientsByStatus(ClientStatus.ACTIVE)).willReturn(clients);

        mockMvc.perform(get("/api/clients/status/{status}", ClientStatus.ACTIVE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testGetClientById() throws Exception {
        UUID clientId = UUID.randomUUID();
        ClientDto client = new ClientDto();
        client.setId(clientId);

        given(clientService.getClientById(clientId)).willReturn(client);

        mockMvc.perform(get("/api/clients/client/{id}", clientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(clientId.toString())));
    }

    @Test
    public void testGetAllClients() throws Exception {
        List<ClientDto> clients = new ArrayList<>();
        clients.add(new ClientDto());

        given(clientService.getAllClients()).willReturn(clients);

        mockMvc.perform(get("/api/clients/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testAddNewClient() throws Exception {
        ClientDto client = new ClientDto();
        client.setFirstName("John");
        client.setLastName("Doe");

        given(clientService.addNewClient(client)).willReturn(client);

        mockMvc.perform(post("/api/clients/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(client)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.lastName", is("Doe")));
    }

    @Test
    public void testDeleteClientById() throws Exception {
        UUID clientId = UUID.randomUUID();

        mockMvc.perform(delete("/api/clients/delete/{id}", clientId))
                .andExpect(status().isOk());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
