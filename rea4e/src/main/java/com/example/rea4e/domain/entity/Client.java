package com.example.rea4e.domain.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "CLIENT")
@AllArgsConstructor
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private UUID id;

    @Column(name = "CLIENT_SECRET")
    private String clientSecret;

    @Column(name = "CLIENT_ID")
    private String clientId;
    
    @Column(name = "REDIRECT_URI")
    private String redirectUri;

    @Column(name = "SCOPES")
    private String scopes;

    // private String grantTypes;
    // private String AuthenticationMethods;
    // private String clientSettings;

    public Client() {

    }
}
