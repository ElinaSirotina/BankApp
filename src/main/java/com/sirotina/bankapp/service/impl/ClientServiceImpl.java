package com.sirotina.bankapp.service.impl;

import com.sirotina.bankapp.dto.ClientDto;
import com.sirotina.bankapp.entity.Client;
import com.sirotina.bankapp.entity.enums.ClientStatus;
import com.sirotina.bankapp.mapper.ClientMapper;
import com.sirotina.bankapp.repository.ClientRepository;
import com.sirotina.bankapp.service.ClientService;
import com.sirotina.bankapp.service.exception.ClientExistException;
import com.sirotina.bankapp.service.exception.ClientNotFoundException;
import com.sirotina.bankapp.service.exception.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {

    private final ClientMapper clientMapper;
    private final ClientRepository clientRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ClientDto> getAllClientsByStatus(ClientStatus status) throws ClientNotFoundException {
        log.info("Get clients with status {}", status);
        return clientMapper.clientsToClientDto
                (clientRepository.findAllByStatus(status).
                        orElseThrow(
                                () -> new ClientNotFoundException
                                        (ErrorMessage.CLIENT_NOT_FOUND_BY_STATUS)));
    }

    @Override
    public List<ClientDto> getAllClients() {
        log.info("Get all clients");
        return clientMapper.clientsToClientDto
                (clientRepository.findAll());
    }



    @Override
    @Transactional(readOnly = true)
    public ClientDto getClientById(UUID id) throws ClientNotFoundException {
        log.info("Get clients with id = {}", id);
        return clientMapper.clientToClientDto(clientRepository.findClientById(id).
                orElseThrow(
                        () -> new ClientNotFoundException
                                (ErrorMessage.CLIENT_NOT_FOUND_BY_ID)));

    }

    @Override
    @Transactional
    public ClientDto addNewClient(ClientDto clientDto) throws ClientExistException {
        log.info("Addition the new Client");
        Client client = clientMapper.clientDtoToClient(clientDto);
        Client existClient = clientRepository.findByTaxCode(client.getTaxCode());
        if(existClient != null)
            throw new ClientExistException("The client with the same Tax Code already exists");

        client.setId(UUID.randomUUID());
        client.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        client.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        log.info("Add new client {}", client);

        clientRepository.save(client);
        return clientMapper.clientToClientDto(client);
    }

    @Override
    @Transactional
    public void deleteClientById(UUID id) {
        log.info("Deleting client {}", id);
        clientRepository.deleteById(id);
    }
}
