package com.sirotina.bankapp.controller;

import com.sirotina.bankapp.dto.ManagerDto;
import com.sirotina.bankapp.service.impl.ManagerServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("api/managers")
@RequiredArgsConstructor
@Tag(name="Managers", description="interaction with managers")
public class ManagerController {

    private final ManagerServiceImpl managerService;

    @Operation(summary = "Getting a list of all managers")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Allows to get a list of all managers",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ManagerDto.class)))
                    })
    })
    @GetMapping("/managers")
    public List<ManagerDto> getAllManagers() {
        return managerService.getAllManagers();
    }

    @Operation(summary = "Getting a manager")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Allows to get a manager by its id",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ManagerDto.class))
                    })
    })
    @GetMapping("/managers/{id}")
    public ManagerDto getInfoAboutAccount(@PathVariable UUID id) {
        return managerService.getManagerById(id);
    }

}
