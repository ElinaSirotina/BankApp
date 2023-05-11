package com.sirotina.bankapp.controller;

import com.sirotina.bankapp.dto.AgreementDto;
import com.sirotina.bankapp.service.AgreementService;
import com.sirotina.bankapp.service.exception.AgreementNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/agreements")
@RequiredArgsConstructor
@Tag(name="Agreements", description="interaction with agreements")
public class AgreementController {

    private final AgreementService agreementService;

    @Operation(
            summary = "Getting all agreements",
            description = "Allows to get a list of agreements"
    )
    @GetMapping("/agreements")
    public ResponseEntity<List<AgreementDto>> getAllAgreements() {
        List<AgreementDto> agreements = agreementService.getAll();
        return new ResponseEntity<>(agreements, HttpStatus.OK);
    }

    @Operation(
            summary = "Getting an agreement",
            description = "Allows to get an agreement by id"
    )
    @GetMapping("/agreement/{id}")
    public ResponseEntity<AgreementDto> getAgreementById(@PathVariable UUID id) throws AgreementNotFoundException {
        AgreementDto agreement = agreementService.getAgreementById(id);
        return new ResponseEntity<>(agreement, HttpStatus.OK);
    }

    @Operation(
            summary = "Creating a new agreement",
            description = "Allows to creat a new agreementе"
    )
    @PostMapping("/create")
    public ResponseEntity<AgreementDto> createAgreement(@RequestBody AgreementDto dto) {
        AgreementDto createdAgreement = agreementService.create(dto);
        return new ResponseEntity<>(createdAgreement, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Updating an agreement",
            description = "Allows to update an agreement by id"
    )
    @PutMapping("update/{id}")
    public ResponseEntity<AgreementDto> updateAgreementById(@PathVariable UUID id, @RequestBody AgreementDto dto)
            throws AgreementNotFoundException {
        AgreementDto updatedAgreement = agreementService.updateAgreementById(id, dto);
        return new ResponseEntity<>(updatedAgreement, HttpStatus.OK);
    }

    @Operation(
            summary = "Deleting an agreement",
            description = "Allows to delete an agreement by id"
    )
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteAgreementById(@PathVariable UUID id) {
        agreementService.deleteAgreementById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
