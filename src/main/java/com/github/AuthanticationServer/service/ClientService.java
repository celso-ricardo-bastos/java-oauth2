package com.github.AuthanticationServer.service;

import com.github.AuthanticationServer.model.Client;
import com.github.AuthanticationServer.repository.ClientRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRespository repository;
    private final PasswordEncoder encoder;

    public void save(Client client) {
        client.setClientSecret(encoder.encode(client.getClientSecret()));
        repository.save(client);
    }

    public Client getClientByClientId(String clientId){
        return repository.findByClientId(clientId);
    }

}
