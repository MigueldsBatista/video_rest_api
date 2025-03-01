package com.example.rea4e.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.example.rea4e.domain.entity.Usuario;

import io.swagger.v3.oas.annotations.Hidden;

@Component
@Hidden
public class SecurityService {

    public Usuario obterUsuarioLogado(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication instanceof CustomAuthentication customAuth) {
            return customAuth.getUsuario();
        }
        
        return null;
    }
}
