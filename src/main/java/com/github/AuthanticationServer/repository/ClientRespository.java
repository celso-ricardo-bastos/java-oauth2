package com.github.AuthanticationServer.repository;

import com.github.AuthanticationServer.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRespository extends JpaRepository<Client, UUID> {
    Client findByClientId(String id);
}
