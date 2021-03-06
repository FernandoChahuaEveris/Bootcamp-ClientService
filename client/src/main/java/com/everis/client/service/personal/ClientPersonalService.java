package com.everis.client.service.personal;

import com.everis.client.dao.entity.personal.ClientPersonal;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ClientPersonalService<T extends ClientPersonal> {

    public Mono<T> createClient(ClientPersonal clientPersonal);

    public Flux<T> findAll();

    public Mono<T> updateClient(UUID id, ClientPersonal clientPersonal);

    public Mono<T> findById(UUID id);

    public Mono<T> deleteClient(UUID id);

    public Mono<T> findClientByDni(String dni);

    public Mono<T> assignClientVip(String dni);

    public String publishMessage(String message);
}
