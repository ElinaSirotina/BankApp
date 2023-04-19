package com.sirotina.bankapp.controller;

import com.sirotina.bankapp.dto.ManagerDto;
import com.sirotina.bankapp.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("api/managers")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    @GetMapping("/managers")
    public List<ManagerDto> getAllManagers() {
        return managerService.getAllManagers();
    }

    @GetMapping("/managers/{id}")
    public ManagerDto getInfoAboutAccount(@PathVariable UUID id) {
        return managerService.getManagerById(id);
    }

}
