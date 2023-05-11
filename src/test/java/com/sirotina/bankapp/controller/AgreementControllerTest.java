package com.sirotina.bankapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sirotina.bankapp.dto.AgreementDto;
import com.sirotina.bankapp.service.AgreementService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AgreementController.class)
public class AgreementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AgreementService agreementService;

    @Test
    public void testGetAllAgreements() throws Exception {
        List<AgreementDto> agreements = new ArrayList<>();
        agreements.add(new AgreementDto());

        given(agreementService.getAll()).willReturn(agreements);

        mockMvc.perform(get("/api/agreements/agreements"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testGetAgreementById() throws Exception {
        UUID agreementId = UUID.randomUUID();
        AgreementDto agreement = new AgreementDto();
        agreement.setId(agreementId);

        given(agreementService.getAgreementById(agreementId)).willReturn(agreement);

        mockMvc.perform(get("/api/agreements/agreement/{id}", agreementId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(agreementId.toString())));
    }

    @Test
    public void testCreateAgreement() throws Exception {
        AgreementDto agreement = new AgreementDto();
        agreement.setId(UUID.randomUUID());

        given(agreementService.create(agreement)).willReturn(agreement);

        mockMvc.perform(post("/api/agreements/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(agreement)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateAgreementById() throws Exception {
        UUID agreementId = UUID.randomUUID();
        AgreementDto agreement = new AgreementDto();
        agreement.setId(agreementId);

        given(agreementService.updateAgreementById(eq(agreementId), any(AgreementDto.class))).willReturn(agreement);

        mockMvc.perform(put("/api/agreements/update/{id}", agreementId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(agreement)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(agreementId.toString())));
    }

    @Test
    public void testDeleteAgreementById() throws Exception {
        UUID agreementId = UUID.randomUUID();

        mockMvc.perform(delete("/api/agreements/delete/{id}", agreementId))
                .andExpect(status().isNoContent());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}