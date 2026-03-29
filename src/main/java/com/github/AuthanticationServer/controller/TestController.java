package com.github.AuthanticationServer.controller;

import com.github.AuthanticationServer.model.Client;
import com.github.AuthanticationServer.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TestController {

    @GetMapping("public")
    public ResponseEntity<String> publicRote() {
        return ResponseEntity.ok("PUBLIC ROUTE!");
    }

    @GetMapping("private")
    public ResponseEntity<String> privateRote() {
        return ResponseEntity.ok("PRIVATE ROUTE!");
    }
}
