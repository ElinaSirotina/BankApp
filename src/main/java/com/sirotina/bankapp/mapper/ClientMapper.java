package com.sirotina.bankapp.mapper;

import com.sirotina.bankapp.dto.ClientDto;
import com.sirotina.bankapp.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface ClientMapper {

    ClientDto clientToClientDto(Client client);

    List<ClientDto> clientsToClientDto(List<Client> clients);

//    @Mapping(target = "managerId", source = "manager.id")
//    @Named("clientToClientDtoWithManagerId")
//    ClientDto clientToClientDtoWithManagerId(Client client);

    Client clientDtoToClient(ClientDto clientDto);
}
