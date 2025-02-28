package com.example.rea4e.rest.dto;

import org.springframework.stereotype.Component;

import com.example.rea4e.domain.entity.Categorias;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "Vídeo", description = "Schema de vídeos")
@Component
@Data//cria os gets e sets
@AllArgsConstructor
@NoArgsConstructor
public class VideoDTO{
    private Long autorId;
    private Long id;
    private String titulo;
    private String descricao;
    private String url;
    private Categorias categoria;

}
