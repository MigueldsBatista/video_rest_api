package com.example.rea4e.security;

import java.io.IOException;
import java.net.Authenticator;

import org.springframework.security.core.Authentication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.rea4e.domain.entity.Usuario;
import com.example.rea4e.domain.service.UsuarioService;
import com.nimbusds.jose.proc.SecurityContext;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtCustomAuthenticationFilter extends OncePerRequestFilter{

    private final UsuarioService usuarioService;

    public JwtCustomAuthenticationFilter(UsuarioService usuarioService){
        this.usuarioService=usuarioService;
    }

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request, 
        @NonNull HttpServletResponse response, 
        @NonNull FilterChain filterChain
        )
        throws ServletException, IOException {
        
            Authentication auth = (Authentication) SecurityContextHolder.getContext().getAuthentication();

            if(deveConverter(auth)){
                String login = auth.getName();
                Usuario usuarioLogado = usuarioService.obterUsuarioPorEmail(login);
                if(usuarioLogado!=null) {
                    auth = new CustomAuthentication(usuarioLogado);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }

            filterChain.doFilter(request, response);
    }

    private boolean deveConverter(Authentication authentication){
        return authentication !=null && authentication instanceof JwtAuthenticationToken;
    }

}
