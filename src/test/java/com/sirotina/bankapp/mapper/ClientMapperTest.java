package com.sirotina.bankapp.mapper;

import com.sirotina.bankapp.dto.ClientDto;
import com.sirotina.bankapp.entity.Client;
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
public class ClientMapperTest {

    @Autowired
    private ClientMapper clientMapper;

    @Test
    public void testClientToClientDto() {
        Client client = new Client();
        UUID id = UUID.randomUUID();
        client.setId(id);
        client.setFirstName("Bob");
        client.setEmail("john.doe@example.com");

        ClientDto clientDto = clientMapper.clientToClientDto(client);

        assertNotNull(clientDto);
        assertEquals(client.getId(), clientDto.getId());
        assertEquals(client.getFirstName(), clientDto.getFirstName());
        assertEquals(client.getEmail(), clientDto.getEmail());
    }

    @Test
    public void testClientsToClientDto() {
        Client client1 = new Client();
        UUID uuid = UUID.randomUUID();
        client1.setId(uuid);
        client1.setFirstName("Bob");
        client1.setEmail("bob_ski@example.com");

        Client client2 = new Client();
        UUID uuid1 = UUID.randomUUID();
        client2.setId(uuid1);
        client2.setFirstName("Bob");
        client2.setEmail("bob_ski@example.com");

        List<Client> clients = Arrays.asList(client1, client2);

        List<ClientDto> clientDtos = clientMapper.clientsToClientDto(clients);

        assertNotNull(clientDtos);
        assertEquals(clients.size(), clientDtos.size());

        assertEquals(clients.get(0).getId(), clientDtos.get(0).getId());
        assertEquals(clients.get(0).getFirstName(), clientDtos.get(0).getFirstName());
        assertEquals(clients.get(0).getEmail(), clientDtos.get(0).getEmail());

        assertEquals(clients.get(1).getId(), clientDtos.get(1).getId());
        assertEquals(clients.get(1).getFirstName(), clientDtos.get(1).getFirstName());
        assertEquals(clients.get(1).getEmail(), clientDtos.get(1).getEmail());
    }

    @Test
    public void testClientDtoToClient() {
        ClientDto clientDto = new ClientDto();
        UUID uuid = UUID.randomUUID();
        clientDto.setId(uuid);
        clientDto.setFirstName("Bob");
        clientDto.setEmail("bob_ski@example.com");

        Client client = clientMapper.clientDtoToClient(clientDto);

        assertNotNull(client);
        assertEquals(clientDto.getId(), client.getId());
        assertEquals(clientDto.getFirstName(), client.getFirstName());
        assertEquals(clientDto.getEmail(), client.getEmail());
    }
}
