package com.example.rea4e.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rea4e.domain.entity.Client;
import com.example.rea4e.domain.service.ClientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Client", description = "Endpoints responsáveis pelas operações com client para intercomunicação de api's")
@RestController
@RequestMapping("api/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService service;

    @Operation(
        summary = "Salvar um cliente",
        description = "Salva um novo cliente. É necessário possuir permissão de administrador para executar essa operação."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso!"),
        @ApiResponse(responseCode = "400", description = "Solicitação inválida para criação do cliente"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Client> salvar(@RequestBody Client entity) {
        Client savedClient = service.salvar(entity);
        // Retorna 201 Created com o cliente criado
        return ResponseEntity.status(201)
                             .body(savedClient);
    }
}
