package com.example.rea4e.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rea4e.domain.entity.Client;
import com.example.rea4e.domain.service.ClientService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("api/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService service;

    @PostMapping
    public ResponseEntity<Client> salvar(@RequestBody Client entity) {
        service.salvar(entity);
        return ResponseEntity.ok(entity);
    }
    
}
