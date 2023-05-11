package com.sirotina.bankapp.service;

import com.sirotina.bankapp.dto.ClientDto;
import com.sirotina.bankapp.entity.Client;
import com.sirotina.bankapp.entity.enums.ClientStatus;
import com.sirotina.bankapp.mapper.ClientMapper;
import com.sirotina.bankapp.repository.ClientRepository;
import com.sirotina.bankapp.service.exception.ClientExistException;
import com.sirotina.bankapp.service.exception.ClientNotFoundException;
import com.sirotina.bankapp.service.impl.ClientServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository repository;

    @Mock
    private ClientMapper mapper;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    public void testGetAllClientsByStatus() throws ClientNotFoundException {
        List<Client> clients = new ArrayList<>();
        clients.add(new Client());

        List<ClientDto> clientDtos = new ArrayList<>();
        clientDtos.add(new ClientDto());

        given(repository.findAllByStatus(any(ClientStatus.class))).willReturn(Optional.of(clients));
        given(mapper.clientsToClientDto(clients)).willReturn(clientDtos);

        List<ClientDto> result = clientService.getAllClientsByStatus(ClientStatus.ACTIVE);

        assertThat(result).isEqualTo(clientDtos);
    }

    @Test(expected = ClientNotFoundException.class)
    public void testGetAllClientsByStatusNotFound() throws ClientNotFoundException {
        given(repository.findAllByStatus(any(ClientStatus.class))).willReturn(Optional.empty());

        clientService.getAllClientsByStatus(ClientStatus.ACTIVE);
    }

    @Test
    public void testGetAllClients() {
        List<Client> clients = new ArrayList<>();
        clients.add(new Client());

        List<ClientDto> clientDtos = new ArrayList<>();
        clientDtos.add(new ClientDto());

        given(repository.findAll()).willReturn(clients);
        given(mapper.clientsToClientDto(clients)).willReturn(clientDtos);

        List<ClientDto> result = clientService.getAllClients();

        assertThat(result).isEqualTo(clientDtos);
    }

    @Test
    public void testGetClientById() throws ClientNotFoundException {
        UUID id = UUID.randomUUID();
        Client client = new Client();
        client.setId(id);

        ClientDto clientDto = new ClientDto();
        clientDto.setId(id);

        given(repository.findClientById(id)).willReturn(Optional.of(client));
        given(mapper.clientToClientDto(client)).willReturn(clientDto);

        ClientDto result = clientService.getClientById(id);

        assertThat(result).isEqualTo(clientDto);
    }

    @Test(expected = ClientNotFoundException.class)
    public void testGetClientByIdNotFound() throws ClientNotFoundException {
        UUID id = UUID.randomUUID();
        given(repository.findClientById(id)).willReturn(Optional.empty());

        clientService.getClientById(id);
    }

    @Test
    public void testAddNewClient() throws ClientExistException {
        ClientDto clientDto = new ClientDto();
        clientDto.setTaxCode("1234567890");
        clientDto.setFirstName("John");
        clientDto.setLastName("Doe");

        Client client = new Client();
        client.setId(UUID.randomUUID());
        client.setTaxCode("1234567890");
        client.setFirstName("John");
        client.setLastName("Doe");
        client.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        client.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        ClientDto savedClientDto = new ClientDto();
        savedClientDto.setId(client.getId());
        savedClientDto.setTaxCode(client.getTaxCode());
        savedClientDto.setFirstName(client.getFirstName());
        savedClientDto.setLastName(client.getLastName());
        savedClientDto.setCreatedAt(client.getCreatedAt());
        savedClientDto.setUpdatedAt(client.getUpdatedAt());

        given(mapper.clientDtoToClient(clientDto)).willReturn(client);
        given(repository.findByTaxCode(client.getTaxCode())).willReturn(null);
        given(repository.save(client)).willReturn(client);
        given(mapper.clientToClientDto(client)).willReturn(savedClientDto);

        ClientDto result = clientService.addNewClient(clientDto);

        assertThat(result).isEqualTo(savedClientDto);
        verify(repository, times(1)).save(any(Client.class));
    }

    @Test(expected = ClientExistException.class)
    public void testAddNewClientAlreadyExists() throws ClientExistException {
        ClientDto clientDto = new ClientDto();
        clientDto.setTaxCode("1234567890");

        Client client = new Client();
        client.setTaxCode("1234567890");

        given(mapper.clientDtoToClient(clientDto)).willReturn(client);
        given(repository.findByTaxCode(client.getTaxCode())).willReturn(client);

        clientService.addNewClient(clientDto);
    }

    @Test
    public void testDeleteClientById() {
        UUID id = UUID.randomUUID();

        clientService.deleteClientById(id);

        verify(repository, times(1)).deleteById(eq(id));
    }
}
