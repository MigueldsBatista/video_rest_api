package com.example.rea4e.security;

import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

import com.example.rea4e.domain.entity.Client;
import com.example.rea4e.domain.service.ClientService;

@Component
public class CustomRegisterdClientRepository implements RegisteredClientRepository{
    
    private final ClientService clientService;
    private final TokenSettings tokenSettings;
    private final ClientSettings clientSettings;
    
    public CustomRegisterdClientRepository(ClientService clientService, TokenSettings tokenSettings, ClientSettings clientSettings) {
        this.clientService = clientService;
        this.tokenSettings = tokenSettings;
        this.clientSettings = clientSettings;
    }
    //NÃO IMPLEMENTADOS
    public RegisteredClient findById(String id) {return null;}

    public void save(RegisteredClient registeredClient) {}

    @Override
    public RegisteredClient findByClientId(String clientId) {
        Client cliente = clientService.buscarPorClientId(clientId);
        
        if(cliente == null) {
            return null;
        }

        return RegisteredClient.
                withId(cliente.getId().toString())//não é o clientId e sim o id do banco
                .clientId(cliente.getClientId())
                .clientSecret(cliente.getClientSecret())
                .redirectUri(cliente.getRedirectUri())
                .scope(cliente.getScopes())
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)//pasasa no header
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)//fluxos de autenticação disponiveis
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)//fluxos de autenticação disponiveis
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)//fluxos de autenticação disponiveis
                .tokenSettings(tokenSettings)
                .clientSettings(clientSettings)
                .build();
    }
    
    
}
