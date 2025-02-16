package com.example.rea4e.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.rea4e.domain.entity.Usuario;
import com.example.rea4e.domain.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{

    private final PasswordEncoder encoder;
    private final UsuarioService usuarioService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String login = authentication.getName();
        String senha = authentication.getCredentials().toString();

        Usuario candidate = usuarioService.obterUsuarioPorEmail(login);

        if(candidate==null){
            throw new UsernameNotFoundException("Usuário e/ou senha incorretos!");
        }

        String encriptedPassword = candidate.getSenha();

        boolean passwordMatches = encoder.matches(senha, encriptedPassword);

        if(passwordMatches==false){
            throw getErroUsuarioNaoEncontrado();
        }
            
        return new CustomAuthentication(candidate);
    }
           
                
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
        
    }

    private UsernameNotFoundException getErroUsuarioNaoEncontrado() {
        throw new UsernameNotFoundException("Usuário e/ou senha incorretos!");
    }

}
