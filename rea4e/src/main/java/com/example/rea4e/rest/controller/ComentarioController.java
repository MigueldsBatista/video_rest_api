package com.example.rea4e.rest.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.rea4e.domain.entity.Comentario;
import com.example.rea4e.domain.service.ComentarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Hidden;

@Tag(name = "Comentários", description = "Endpoints para gerenciamento de comentários")
@RestController
@RequestMapping("api/comentarios")
public class ComentarioController {

    private final ComentarioService servico;

    public ComentarioController(ComentarioService servico) {
        this.servico = servico;
    }

    @PostMapping
    @Operation(summary = "Salvar um novo comentário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comentário salvo com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro ao salvar comentário")
    })
    public ResponseEntity<Comentario> salvar(@RequestBody Comentario comentario) {
        Comentario comentarioSalvo = servico.salvar(comentario);
        return ResponseEntity.ok(comentarioSalvo);
    }

    @GetMapping("/{id}")
    @Hidden
    public ResponseEntity<Comentario> buscarPorId(@PathVariable Long id) {
        Comentario comentario = servico.buscarPorId(id);
        return ResponseEntity.ok(comentario);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um comentário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Comentário deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Comentário não encontrado")
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        servico.deletar(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um comentário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comentário atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro ao atualizar comentário")
    })
    public ResponseEntity<Comentario> atualizar(@PathVariable Long id, @RequestBody Comentario comentario) {
        Comentario comentarioAtualizado = servico.salvar(comentario);
        return ResponseEntity.ok(comentarioAtualizado);
    }

    @GetMapping("/video/{videoId}")
    @Hidden
    public ResponseEntity<List<Comentario>> listarComentariosPorvideo(@PathVariable Long videoId) {
        List<Comentario> comentario = servico.listarComentariosPorvideo(videoId);
        return ResponseEntity.ok(comentario);
    }

    @GetMapping("/video/{videoId}/contar")
    @Hidden
    public ResponseEntity<Integer> contarComentariosPorvideo(@PathVariable Long videoId) {
        Integer comentario = servico.contarComentariosPorvideo(videoId);
        return ResponseEntity.ok(comentario);
    }
}
