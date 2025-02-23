package com.example.rea4e.rest.dto;

import java.util.HashSet;
import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//anotação do swagger
@Schema(name = "Usuário", description = "Schema de usuários")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String email;
    private String nome;
    private String senha;
    private Set<String> permissoes = new HashSet<String>();
}
