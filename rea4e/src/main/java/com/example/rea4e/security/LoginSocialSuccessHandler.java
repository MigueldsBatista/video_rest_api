package com.example.rea4e.security;

import java.io.IOException;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.rea4e.domain.entity.Usuario;
import com.example.rea4e.domain.service.UsuarioService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginSocialSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    
    private final UsuarioService service;
    
    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication) throws ServletException, IOException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;

        OAuth2User user = token.getPrincipal();

        String email = user.getAttribute("email");

        Usuario usuarioLogado = service.obterUsuarioPorEmail(email);

        if(usuarioLogado==null){
            Usuario usuarioCriado = new Usuario();
            usuarioCriado.setEmail(email);
            usuarioCriado.setNome(email);
            usuarioCriado.setSenha("123");
            // Set default permissions using Set.of to create an immutable set of permissions
            usuarioCriado.setPermissoes(Set.of("USUARIO", "ADMIN"));
            service.salvar(usuarioCriado);
            usuarioLogado = usuarioCriado;
        }

        CustomAuthentication customAuth = new CustomAuthentication(usuarioLogado);

        SecurityContextHolder.getContext().setAuthentication(customAuth);

        super.onAuthenticationSuccess(request, response, customAuth);
    }

}
