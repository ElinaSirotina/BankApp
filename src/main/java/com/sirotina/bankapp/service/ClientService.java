package com.sirotina.bankapp.service;

import com.sirotina.bankapp.dto.ClientDto;
import com.sirotina.bankapp.entity.enums.ClientStatus;
import com.sirotina.bankapp.service.exception.ClientExistException;
import com.sirotina.bankapp.service.exception.ClientNotFoundException;

import java.util.List;
import java.util.UUID;

public interface ClientService {

    List<ClientDto> getAllClientsByStatus(ClientStatus status) throws ClientNotFoundException;

    ClientDto getClientById(UUID id) throws ClientNotFoundException;

    List<ClientDto> getAllClients();

    ClientDto addNewClient(ClientDto clientDto) throws ClientExistException;


    void deleteClientById(UUID id);

}
