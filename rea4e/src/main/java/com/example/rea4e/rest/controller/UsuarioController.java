package com.example.rea4e.rest.controller;

import java.util.List;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.rea4e.domain.entity.Usuario;
import com.example.rea4e.domain.entity.Video;
import com.example.rea4e.domain.service.UsuarioService;
import com.example.rea4e.rest.dto.UsuarioDTO;
import com.example.rea4e.rest.dto.VideoDTO;
import com.example.rea4e.rest.mapper.UsuarioMapper;
import com.example.rea4e.rest.mapper.VideoMapper;
import com.example.rea4e.security.SecurityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;


@Tag(name = "Usuário", description = "Endpoints responsáveis pelas operações com usuários")
@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
    private UsuarioService usr;
    private UsuarioMapper userMapper;
    private VideoMapper videoMapper;

    public UsuarioController(UsuarioService usr, UsuarioMapper mapper, VideoMapper videoMapper) {
        this.usr = usr;
        this.userMapper = mapper;
        this.videoMapper = videoMapper;
    }

    @Operation(summary = "Obter usuário logado", description = "Retorna os detalhes do usuário autenticado")
    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<UsuarioDTO> obterUsuarioLogado(SecurityService sec) {
        return ResponseEntity.ok(userMapper.toDTO(sec.obterUsuarioLogado()));
    }

    @Operation(summary = "Buscar usuário por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@Parameter(description = "ID do usuário", required = true) @PathVariable Long id) {
        Usuario usuario = usr.buscarPorId(id);
        return ResponseEntity.ok(userMapper.toDTO(usuario));
    }

    @Operation(summary = "Criar um novo usuário")
    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso")
    @PostMapping
    public ResponseEntity<UsuarioDTO> salvar(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = userMapper.toUsuario(usuarioDTO);
        return new ResponseEntity<>(userMapper.toDTO(usr.salvar(usuario)), HttpStatus.CREATED);
    }

    @Operation(summary = "Deletar um usuário", description = "Remove um usuário pelo ID")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usr.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Favoritar um vídeo")
    @PostMapping("/{usuarioId}/favoritar/{videoId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Void> favoritarAula(@PathVariable Long usuarioId, @PathVariable Long videoId) {
        usr.favoritarVideo(usuarioId, videoId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar vídeos favoritos do usuário")
    @GetMapping("/{usuarioId}/favoritos")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<VideoDTO>> listarFavoritos(@PathVariable Long usuarioId) {
        Usuario usuario = usr.buscarPorId(usuarioId);
        return ResponseEntity.ok(videoMapper.toDTOList(usuario.getVideosFavoritos()));
    }

    @Operation(summary = "Desfavoritar um vídeo")
    @DeleteMapping("/{usuarioId}/favoritar/{videoId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Void> desfavoritarAula(@PathVariable Long usuarioId, @PathVariable Long videoId) {
        usr.desfavoritarVideo(usuarioId, videoId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualizar dados do usuário")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<UsuarioDTO> atualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        usuario.setId(id);
        return ResponseEntity.ok(userMapper.toDTO(usr.salvar(usuario)));
    }

    @Operation(summary = "Adicionar permissão a um usuário")
    @PostMapping("/{usuarioId}/permissao")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioDTO> adicionarPermissao(@PathVariable Long usuarioId, @RequestBody String permissaoAdicionada) {
        usr.adicionarPermissaoUsuario(usuarioId, permissaoAdicionada);
        return ResponseEntity.ok(userMapper.toDTO(usr.buscarPorId(usuarioId)));
    }

    @Operation(summary = "Remover permissão de um usuário")
    @DeleteMapping("/{usuarioId}/permissao")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removerPermissao(@PathVariable Long usuarioId, @RequestBody String permissaoRemovida) {
        usr.removerPermissaoUsuario(usuarioId, permissaoRemovida);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obter permissões do usuário")
    @GetMapping("/{usuarioId}/permissao")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Set<String>> obterPermissoes(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(usr.buscarPorId(usuarioId).getPermissoes());
    }

    @Operation(summary = "Verificar disponibilidade de email")
    @GetMapping("/email")
    public ResponseEntity<Boolean> verificarEmail(@RequestBody String email) {
        return ResponseEntity.ok(usr.obterUsuarioPorEmail(email) == null);
    }
}
