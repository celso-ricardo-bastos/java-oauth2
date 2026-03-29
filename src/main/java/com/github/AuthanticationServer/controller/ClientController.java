package com.github.AuthanticationServer.controller;

import com.github.AuthanticationServer.model.Client;
import com.github.AuthanticationServer.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("client")
@RequiredArgsConstructor
@Slf4j
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> save(@RequestBody Client client) {
        log.info("Client as save with success.");
        clientService.save(client);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
