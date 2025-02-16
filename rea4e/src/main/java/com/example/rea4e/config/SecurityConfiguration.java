package com.example.rea4e.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

    private final LoginSocialSuccessHandler socialHandler;
    @Bean//Estamos sobrescrevendo o padrao do Spring Security
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
        .csrf(AbstractHttpConfigurer::disable)
        .httpBasic(Customizer.withDefaults())
        .formLogin(configurer ->{
            configurer.loginPage("/login");
        })
        // .formLogin(Customizer.withDefaults())
        .authorizeHttpRequests(authorizer -> {
            authorizer.requestMatchers("/login").permitAll();
            authorizer.requestMatchers(HttpMethod.POST, "api/usuario/**").permitAll();
            authorizer.anyRequest().authenticated();


        })//Vamos autorizar todas as requisições
        .oauth2Login(oauth2 -> {
            oauth2
            .loginPage("/login")
            .successHandler(socialHandler);
        })
        .build();

        //Desse modo é igual o padrao da biblioteca 
    }

    //Com o bcrypt o hash é gerado de forma aleatória, nao é possivel fazer um
    //traceback, pra fazer o match o encoder verifica se é possivel gerar o mesmo hash a partir daquela senha
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    public UserDetailsService userDetailsService(UsuarioService usr){
        return new CustomUserDetailService(usr);
    }

    // @Bean
    // public DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder encoder) {
    //     DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    //     provider.setUserDetailsService(userDetailsService);
    //     provider.setPasswordEncoder(encoder);
    //     return provider;
    // }
    //Por algum motivo quando eu escrevi esse código, rodei e depois comentei ele  continuou funcionando

    @Bean WebSecurityCustomizer webSecurityCustomizer(){
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
    public GrantedAuthorityDefaults grantedAuthorityDefaults(){
        return new GrantedAuthorityDefaults("");//vai eliminar o uso do role
    }


}
