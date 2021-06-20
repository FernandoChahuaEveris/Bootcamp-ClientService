package com.everis.client.service.enterprise;

import com.everis.client.dao.entity.enterprise.ClientEnterprise;
import com.everis.client.dao.entity.enterprise.EnterpriseError;
import com.everis.client.dao.entity.personal.PersonalError;
import com.everis.client.dao.repository.enterprise.ClientEnterpriseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.UUID;

public class ClientEnterpriseServiceImpl implements ClientEnterpriseService<ClientEnterprise>{

    @Autowired
    ClientEnterpriseRepository<ClientEnterprise> repository;

    @Override
    public Mono<ClientEnterprise> createClient(ClientEnterprise clientEnterprise) {
        String id = UUID.randomUUID().toString();
        return Mono.just(clientEnterprise)
                .map(enterprise -> {
                    enterprise.setIdClient(UUID.fromString(id));
                    enterprise.setTypeClient("Empresarial");
                    enterprise.setCreationDate(new Date());
                    return enterprise;
                })
                .flatMap(enterprise -> repository.save(enterprise));
    }

    @Override
    public Flux<ClientEnterprise> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<ClientEnterprise> updateClient(UUID id, ClientEnterprise clientEnterprise) {
         return repository.findById(id)
                .filter(enterprise ->
                        id.equals(enterprise.getIdClient())
                )
                .flatMap(enterprise -> {
                    enterprise.setRuc(clientEnterprise.getRuc() != null? clientEnterprise.getRuc() : enterprise.getRuc());
                    enterprise.setBusinessName(clientEnterprise.getBusinessName() != null? clientEnterprise.getBusinessName() : enterprise.getBusinessName());
                    return repository.save(enterprise);
                })
                .switchIfEmpty(Mono.just(new EnterpriseError(HttpStatus.NOT_FOUND, "No se encontro")));
    }

    @Override
    public Mono<ClientEnterprise> findById(UUID id) {
            return repository
                    .findById(id)
                    .switchIfEmpty(
                            Mono.just(new EnterpriseError(HttpStatus.NOT_FOUND, "No se encontro registro"))
                    );
    }

    @Override
    public Mono<ClientEnterprise> deleteClient(UUID id) {
        return repository.findById(id)
                .flatMap(p ->
                        repository.deleteById(id).thenReturn(p)
                );
    }
}
