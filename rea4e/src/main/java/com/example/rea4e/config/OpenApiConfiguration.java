package com.example.rea4e.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

//vai modificar o cabeçalho da página do swagger
@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Video Rest API",
        version = "1.0",
        description = "Uma para aprimorar meus conhecimentos em Spring Boot, Spring Security, Orientação a objeto, Design e Arquitetura de sistemas, MySQL e muito mais...",
        contact = @Contact(name = "Miguel de Sousa Batista", email = "miguelsbatista0610@gmail.com", url = "http://github.com/MigueldsBatista"),
        license = @License(name = "MIT" )
    ),
    security = {
        @SecurityRequirement(name = "bearerAuth"),

    }
)
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "Bearer", in = SecuritySchemeIn.HEADER)
public class OpenApiConfiguration {


}
