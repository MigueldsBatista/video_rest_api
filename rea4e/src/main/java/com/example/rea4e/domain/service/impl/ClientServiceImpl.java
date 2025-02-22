package com.example.rea4e.domain.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.rea4e.domain.entity.Client;
import com.example.rea4e.domain.repository.ClientRepository;
import com.example.rea4e.domain.service.BaseService;
import com.example.rea4e.domain.service.ClientService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ClientServiceImpl extends BaseService<Client, UUID> implements ClientService{

    private final ClientRepository clientRepository;
    
    public ClientServiceImpl(ClientRepository clientRepository){
        this.clientRepository=clientRepository;
    }

    @Override
    public Client buscarPorClientId(String clientId) {
        
        return clientRepository.findByClientId(clientId);
        
    }
}
