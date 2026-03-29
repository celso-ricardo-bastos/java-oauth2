package com.github.AuthanticationServer.service;

import com.github.AuthanticationServer.model.Usuario;
import com.github.AuthanticationServer.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;

    public void save(Usuario usuario) {
        String senha = usuario.getSenha();
        usuario.setSenha(encoder.encode(senha));
        repository.save(usuario);
    }

    public Usuario obterPorLogin(String login){
          return repository.findByLogin(login);
    }
}
