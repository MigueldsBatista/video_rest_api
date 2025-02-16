package com.example.rea4e.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.rea4e.domain.entity.Usuario;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CustomAuthentication implements Authentication{

    private final Usuario usuario;

    @Override
    public String getName() {
        
        return usuario.getNome();
    }

    @Override
    public Object getDetails() {
        return usuario;
    }

    @Override
    public Object getPrincipal() {
        return usuario;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return usuario.
        getPermissoes().
        stream().
        map(role -> new SimpleGrantedAuthority(role)).
        collect(Collectors.toList());
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public Object getCredentials() {
        return usuario.getSenha();
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        
    }
}
