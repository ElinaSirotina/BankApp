package com.sirotina.bankapp.controller;

import com.sirotina.bankapp.dto.ClientDto;
import com.sirotina.bankapp.entity.enums.ClientStatus;
import com.sirotina.bankapp.service.ClientService;
import com.sirotina.bankapp.service.exception.ClientExistException;
import com.sirotina.bankapp.service.exception.ClientNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/clients")
@RequiredArgsConstructor
@Tag(name="Clients", description="interaction with clients")
public class ClientController {

    private final ClientService clientService;

    @Operation(
            summary = "Getting clients by status",
            description = "Allows to get all clients by status."
    )
    @GetMapping("/status/{status}")
    @ResponseStatus(HttpStatus.OK)
    public List<ClientDto> getAllClientsByStatus(@PathVariable ClientStatus status) throws ClientNotFoundException {
        return clientService.getAllClientsByStatus(status);
    }

    @Operation(
            summary = "Getting a client",
            description = "Allows to get a client by id"
    )
    @GetMapping("/client/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClientDto getClientById(@PathVariable UUID id) throws ClientNotFoundException {
        return clientService.getClientById(id);
    }

    @Operation(
            summary = "Getting all clients",
            description = "Allows to get all clients"
    )
    @GetMapping("/clients")
    @ResponseStatus(HttpStatus.OK)
    public List<ClientDto> getAllClients() {
        return clientService.getAllClients();
    }

    @Operation(
            summary = "Add a client",
            description = "Allows to add a client"
    )
    @PostMapping(path = "/add", consumes = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDto addNewClient(@RequestBody ClientDto clientDto) throws ClientExistException {
        return clientService.addNewClient(clientDto);
    }

    @Operation(
            summary = "Delete a client",
            description = "Allows to delete a client by id"
    )
    @DeleteMapping("/delete/{id}")
    public void deleteClientById(@PathVariable UUID id) {
        clientService.deleteClientById(id);
    }
}
