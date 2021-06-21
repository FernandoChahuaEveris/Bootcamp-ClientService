package com.everis.client.dao.repository.personal;

import com.everis.client.dao.entity.personal.ClientPersonal;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface ClientPersonalRepository<T extends ClientPersonal> extends ReactiveMongoRepository<T, UUID> {
    public Mono<T> findClientByDni(String dni);
}
