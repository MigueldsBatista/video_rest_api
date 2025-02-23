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

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Autenticação", description = "Endpoints para gerenciamento de login")
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
    @Hidden
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // Retorna a página de login (login.html)
    }

    @PostMapping("/login")
    @Operation(summary = "Autenticar usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "302", description = "Redirecionamento para a página inicial após login bem-sucedido"),
        @ApiResponse(responseCode = "302", description = "Redirecionamento para a página de login com erro em caso de falha")
    })
    public String login(@RequestParam String username, @RequestParam String password) {
        try {
            authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
            );

            Usuario usuario = usuarioService.obterUsuarioPorEmail(username);
            CustomAuthentication customAuth = new CustomAuthentication(usuario);
            SecurityContextHolder.getContext().setAuthentication(customAuth);

            return "redirect:/";
        } catch (Exception e) {
            return "redirect:/login?error=true";
        }
    }

    @Hidden
    @GetMapping("/")
    @ResponseBody
    public String homePage() {
        return "Bem vindo! " + securityService.obterUsuarioLogado().getEmail(); // Retorna a página inicial (home.html)
    }

    @Hidden
    @GetMapping("/authorized")
    @ResponseBody
    public String getAuthorizationCode(@RequestParam("code") String code) {
        return "Seu authorization code: "+code; 
    }
    
}