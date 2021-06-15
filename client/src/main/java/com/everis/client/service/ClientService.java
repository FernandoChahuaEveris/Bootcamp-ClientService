package com.everis.client.service;

import com.everis.client.dao.entity.Client;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService {

    public Mono<Client> createClient(Client client);

    public Flux<Client> findAll();
}
