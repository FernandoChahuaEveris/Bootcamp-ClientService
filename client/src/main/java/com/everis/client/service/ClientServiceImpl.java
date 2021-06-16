package com.everis.client.service;

import com.everis.client.dao.entity.Client;
import com.everis.client.dao.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;
@Slf4j
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
        return clientRepository.findAll();
    }

    @Override
    public Mono<Client> updateClient(UUID id, Client client) {
        log.info("client " + client.getName());
        return clientRepository.findById(id)
                .map(e -> client)
                .flatMap(e -> clientRepository.save(e));
    }

    @Override
    public Mono<Client> findById(UUID id) {
        log.info("idRequest "+ id);
        Mono<Client> client = clientRepository.findById(id);
        client.doOnNext(element -> {
            element.getIdClient();
        })
        .switchIfEmpty(Mono.error(new Exception("No se encontro")));
        //log.info("object "+ );
        return null;
    }

    @Override
    public Mono<Client> deleteClient(UUID id) {
        return clientRepository.findById(id)
                .flatMap(p ->
                    clientRepository.deleteById(id).thenReturn(p)
                );
    }

}
