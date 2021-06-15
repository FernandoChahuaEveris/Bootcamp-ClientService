package com.everis.client.service;

import com.everis.client.dao.entity.Client;
import com.everis.client.dao.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class ClientServiceImpl implements ClientService{

    @Autowired
    private ClientRepository clientRepository;


    @Override
    public Mono<Client> createClient(Client client) {
        return Mono.just(client)
                .map(e -> {
                    e.setIdClient(UUID.randomUUID());
                    return e;
                })
                .flatMap(e -> clientRepository.save(e));
    }

    @Override
    public Flux<Client> findAll() {
        return null;
    }
}
