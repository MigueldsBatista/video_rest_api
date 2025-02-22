package com.example.rea4e.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.rea4e.domain.entity.Usuario;
import com.example.rea4e.domain.exception.DuplicateException;
import com.example.rea4e.domain.publisher.UsuarioEventPublisher;
import com.example.rea4e.domain.repository.UsuarioRepository;
import com.example.rea4e.domain.service.BaseService;
import com.example.rea4e.domain.service.UsuarioService;
import com.example.rea4e.security.SecurityService;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class UsuarioServiceImpl extends BaseService<Usuario, Long> implements UsuarioService {
    
    private final UsuarioEventPublisher eventPublisher;
    private final UsuarioRepository repositorio;
    private final SecurityService sec;
    private final PasswordEncoder encoder;

    public UsuarioServiceImpl(UsuarioRepository repositorio, UsuarioEventPublisher eventPublisher, SecurityService sec, PasswordEncoder encoder) {
        this.repositorio = repositorio;
        this.eventPublisher = eventPublisher;
        this.sec = sec;
        this.encoder = encoder;
    }
    
    
    public void favoritarVideo(Long usuarioId, Long videoId){
        eventPublisher.publishvideoFavoritado(usuarioId, videoId);
    }
    
    public void desfavoritarVideo(Long usuarioId, Long videoId){
        eventPublisher.publishvideoDesfavoritado(usuarioId, videoId);
    }

   
    @Override
    public void adicionarPermissaoUsuario(Long usuarioId, String permissao) {
        if(permissao.isEmpty()){
            throw new IllegalArgumentException("A permissão não pode ser uma string vazia.");
        }
        eventPublisher.publishAdicionarPermissao(usuarioId, permissao);        
    }

    @Override
    public void removerPermissaoUsuario(Long usuarioId, String permissao) {
        if(permissao.isEmpty()){
            throw new IllegalArgumentException("A permissão não pode ser uma string vazia.");
        }

        eventPublisher.publishRemoverPermissao(usuarioId, permissao);
    }


    @Override
    public Usuario salvar(Usuario usuario) {
        if(obterUsuarioPorEmail(usuario.getEmail())!=null){
            throw new DuplicateException("Ja existe um usuário com esse email!");
        }

        Usuario usuarioLogado = sec.obterUsuarioLogado();

        if(usuarioLogado!=null && isUserSaved(usuario)==false){
            usuario.setCriador(usuario);
        }
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        return super.salvar(usuario);
    }


    @Override
    public Usuario obterUsuarioPorEmail(String email) {
        //Validações aqui
        return repositorio.findByEmail(email);
        
    }
    
    public boolean isUserSaved(Usuario usuario){
        if(usuario==null) return false;

        Usuario candidato = buscarPorId(usuario.getId());
        return candidato == null ? false : true;
    }

    public boolean isUserSaved(Long usuarioId){
        return isUserSaved(buscarPorId(usuarioId));
    }
    
    public boolean isUserSaved(String username){
        return isUserSaved(obterUsuarioPorEmail(username));
    }
}

