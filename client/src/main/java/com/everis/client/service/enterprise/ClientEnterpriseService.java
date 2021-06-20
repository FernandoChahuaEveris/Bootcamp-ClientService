package com.everis.client.service.enterprise;

import com.everis.client.dao.entity.enterprise.ClientEnterprise;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ClientEnterpriseService<T extends ClientEnterprise> {

    public Mono<T> createClient(ClientEnterprise clientEnterprise);

    public Flux<T> findAll();

    public Mono<T> updateClient(UUID id, ClientEnterprise clientEnterprise);

    public Mono<T> findById(UUID id);

    public Mono<T> deleteClient(UUID id);
}
