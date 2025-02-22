package com.example.rea4e.domain.service.impl;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;
    public ClientServiceImpl(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository=clientRepository;
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    public Client buscarPorClientId(String clientId) {
        return clientRepository.findByClientId(clientId);
        
    }

    @Override
    public Client salvar(Client entity) {
        String encoded = passwordEncoder.encode(entity.getClientSecret());
        entity.setClientSecret(encoded);
        return super.salvar(entity);
    }
}
