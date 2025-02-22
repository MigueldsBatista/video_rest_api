package com.example.rea4e.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.rea4e.domain.service.UsuarioService;
import com.example.rea4e.security.CustomUserDetailService;
import com.example.rea4e.security.LoginSocialSuccessHandler;

import lombok.RequiredArgsConstructor;

//para interagir com mysql eu uso

/*

@Configuration é uma anotação de configuração que indica que a classe é uma classe de configuração do Spring, ou seja, ela contém métodos que configuram o contexto da aplicação.

 @EnableWebSecurity é uma anotação de configuração que importa automaticamente a configuração de segurança padrão do Spring que contem as seguintes funcionalidades
    - Um filtro de segurança que protege a aplicação de ataques
    - Um filtro que gerencia a autenticação dos usuários
    - Um filtro que gerencia a autorização dos usuários
    - Um filtro que gerencia a sessão dos usuários
    
 
 SecurityFilterChain é um filtro de segurança que é responsável por proteger a aplicação de ataques, ele intercepta as requisições e verifica se o usuário tem permissão para acessar a aplicação.
 
 */


 @Configuration
 @EnableWebSecurity
 @EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
 public class SecurityConfiguration {
 
     private final LoginSocialSuccessHandler socialHandler;

        public SecurityConfiguration(@Lazy LoginSocialSuccessHandler socialHandler) {
            this.socialHandler = socialHandler;
        }
 
     @Bean
     SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         return http
             .csrf(AbstractHttpConfigurer::disable) // Desabilita CSRF (opcional para APIs REST)
             .httpBasic(Customizer.withDefaults()) // Habilita autenticação básica (opcional)
             .formLogin(configurer -> {
                 configurer
                     .loginPage("/login") // Página de login personalizada
                     .defaultSuccessUrl("/") // Redireciona para a página inicial após o login bem-sucedido
                     .failureUrl("/login?error=true"); // Redireciona para "/login" com um parâmetro de erro em caso de falha
             })
             .authorizeHttpRequests(authorizer -> {
                 authorizer
                     .requestMatchers("/login").permitAll() // Permite acesso à página de login
                     .requestMatchers(HttpMethod.POST, "api/usuario/**").permitAll() // Permite cadastro de usuários
                     .anyRequest().authenticated(); // Todas as outras requisições exigem autenticação
             })
             .oauth2Login(oauth2 -> {
                 oauth2
                     .loginPage("/login") // Página de login personalizada
                     .successHandler(socialHandler); // Handler para redirecionamento após o login social
             })
             .build();
     }
 
     @Bean
     PasswordEncoder passwordEncoder() {
         return new BCryptPasswordEncoder(10); // Configura o BCryptPasswordEncoder com força 10
     }
 
     @Bean
     UserDetailsService userDetailsService(UsuarioService usr) {
         return new CustomUserDetailService(usr); // Configura o UserDetailsService personalizado
     }
 

     @Bean
     WebSecurityCustomizer webSecurityCustomizer() {
         return (web) -> {
             web.ignoring().requestMatchers(
                 "/v2/api-docs/**",
                 "/v3/api-docs/**",
                 "/swagger-resources/**",
                 "/swagger-ui/**",
                 "/swagger-ui.html",
                 "/webjars/**"
             );
         };
     }
 
     @Bean
     GrantedAuthorityDefaults grantedAuthorityDefaults() {
         return new GrantedAuthorityDefaults(""); // Remove o prefixo "ROLE_" das roles
     }
 }
