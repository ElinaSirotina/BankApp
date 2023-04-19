package com.sirotina.bankapp.mapper;


import com.sirotina.bankapp.dto.ManagerDto;
import com.sirotina.bankapp.entity.Manager;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ManagerMapper {

    ManagerDto toDto(Manager manager);

    List<ManagerDto> managersToManagersDto(List<Manager> managers);

}
