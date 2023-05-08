package com.sirotina.bankapp.service;

import com.sirotina.bankapp.entity.Client;
import com.sirotina.bankapp.entity.enums.ClientStatus;
import com.sirotina.bankapp.mapper.ClientMapper;
import com.sirotina.bankapp.repository.ClientRepository;
import com.sirotina.bankapp.service.exception.ClientNotFoundException;
import com.sirotina.bankapp.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTest {

    @Mock
    private ClientMapper clientMapper;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

}
