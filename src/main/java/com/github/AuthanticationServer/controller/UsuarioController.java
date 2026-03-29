package com.github.AuthanticationServer.controller;

import com.github.AuthanticationServer.model.Usuario;
import com.github.AuthanticationServer.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody Usuario usuario) {
        service.save(usuario);
    }
}
