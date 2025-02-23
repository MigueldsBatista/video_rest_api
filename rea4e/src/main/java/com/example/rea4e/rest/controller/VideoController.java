package com.example.rea4e.rest.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.rea4e.domain.entity.Categorias;
import com.example.rea4e.domain.entity.Video;
import com.example.rea4e.domain.service.VideoService;
import com.example.rea4e.rest.dto.VideoDTO;
import com.example.rea4e.rest.mapper.VideoMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Tag(name = "Vídeos", description = "Endpoints para gerenciamento de vídeos")
@RestController
@RequestMapping("/api/video")
public class VideoController {

    private final VideoService servico;
    private final VideoMapper mapper;

    public VideoController(VideoService servico, VideoMapper mapper) {
        this.mapper = mapper;
        this.servico = servico;
    }

    @Operation(summary = "Listar todos os vídeos")
    @GetMapping
    public List<VideoDTO> listar() {
        return mapper.toDTOList(servico.listar());
    }

    @Operation(summary = "Listar vídeos por autor")
    @GetMapping("/autor/{autorId}")
    public List<VideoDTO> listarvideosPorAutor(@PathVariable Long autorId) {
        return mapper.toDTOList(servico.listarvideosPorAutor(autorId));
    }

    @Operation(summary = "Listar vídeos por categoria")
    @GetMapping("/categoria/{categoria}")
    public List<VideoDTO> listarvideosPorCategoria(@PathVariable String categoria) {
        Categorias cat = Categorias.valueOf(categoria);
        return mapper.toDTOList(servico.listarvideosPorCategoria(cat));
    }

    @Operation(summary = "Buscar vídeo por ID")
    @GetMapping("/{id}")
    public ResponseEntity<VideoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toDTO(servico.buscarPorId(id)));
    }

    @Operation(summary = "Salvar um novo vídeo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Vídeo criado com sucesso")
    })
    @PostMapping
    public ResponseEntity<VideoDTO> salvar(@RequestBody VideoDTO videoDTO) {
        Video video = mapper.toVideo(videoDTO);
        Video salvo = servico.salvar(video);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(salvo));
    }

    @Operation(summary = "Atualizar um vídeo")
    @PutMapping("/{id}")
    public ResponseEntity<VideoDTO> atualizar(@PathVariable Long id, @RequestBody VideoDTO videoDTO) {
        Video video = mapper.toVideo(videoDTO);
        video.setId(id);
        Video atualizado = servico.salvar(video);
        return ResponseEntity.ok(mapper.toDTO(atualizado));
    }

    @Operation(summary = "Deletar um vídeo")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        servico.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar todas as categorias disponíveis")
    @GetMapping("/listar-categorias")
    public ResponseEntity<Set<String>> listarCategorias() {
        Set<String> categorias = Arrays.stream(Categorias.values())
                                       .map(Categorias::name)
                                       .collect(Collectors.toSet());
        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }
}
