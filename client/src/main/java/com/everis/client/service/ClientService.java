package com.everis.client.service;

import com.everis.client.dao.entity.Client;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ClientService {

    public Mono<Client> createClient(Client client);

    public Flux<Client> findAll();

    public Mono<Client> updateClient(UUID id, Client client);

    public Mono<ResponseEntity> findById(UUID id);

    public Mono<Client> deleteClient(UUID id);
}
