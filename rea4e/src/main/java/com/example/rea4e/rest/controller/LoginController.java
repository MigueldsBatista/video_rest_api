package com.example.rea4e.rest.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.rea4e.domain.entity.Usuario;
import com.example.rea4e.domain.service.UsuarioService;
import com.example.rea4e.security.CustomAuthentication;
import com.example.rea4e.security.SecurityService;

@Controller
public class LoginController {

    private final AuthenticationProvider authenticationProvider;
    private final SecurityService securityService;
    private final UsuarioService usuarioService;

    public LoginController(AuthenticationProvider authenticationProvider, SecurityService securityService, UsuarioService usuarioService) {
        this.authenticationProvider = authenticationProvider;
        this.securityService = securityService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // Retorna a página de login (login.html)
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        try {
            // Cria um token de autenticação com as credenciais fornecidas
            authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)//Uma das implementações do Authentication
            );

            // Define a autenticação no contexto de segurança
            Usuario usuario = usuarioService.obterUsuarioPorEmail(username);

            CustomAuthentication customAuth = new CustomAuthentication(usuario);

            SecurityContextHolder.getContext().setAuthentication(customAuth);

            // Redireciona para a página inicial após o login bem-sucedido
            return "redirect:/";
        } catch (Exception e) {
            // Em caso de falha, redireciona de volta para a página de login com um parâmetro de erro
            return "redirect:/login?error=true";
        }
    }

    @GetMapping("/")
    @ResponseBody
    public String homePage() {
        return "Bem vindo! " + securityService.obterUsuarioLogado().getEmail(); // Retorna a página inicial (home.html)
    }

    @GetMapping("/authorized")
    public String getAuthorizationCode(@RequestParam("code") String code) {
        return "Seu authorization code: "+code; 
    }
    
}