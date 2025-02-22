package com.example.rea4e.domain.service;


import java.util.UUID;

import com.example.rea4e.domain.entity.Client;

public interface ClientService {
    Client salvar(Client comentario);
    Client buscarPorId(UUID id);
    void deletar(UUID id);
    Client buscarPorClientId(String clientId);
}
