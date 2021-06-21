package com.everis.client.service.personal;

import com.everis.client.dao.entity.personal.ClientPersonal;
import com.everis.client.dao.entity.personal.PersonalError;
import com.everis.client.dao.repository.personal.ClientPersonalRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class ClientPersonalServiceImpl implements ClientPersonalService<ClientPersonal> {

    @Autowired
    ClientPersonalRepository<ClientPersonal> repository;


    @Override
    public Mono<ClientPersonal> createClient(ClientPersonal clientPersonal) {
        String id = UUID.randomUUID().toString();
        return Mono.just(clientPersonal)
                .map(personal -> {
                    personal.setIdClient(UUID.fromString(id));
                    personal.setTypeClient("Personal");
                    personal.setCreationDate(new Date());
                    return personal;
                })
                .flatMap(personal -> repository.save(personal));
    }

    @Override
    public Flux<ClientPersonal> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<ClientPersonal> updateClient(UUID id, ClientPersonal clientPersonal) {

        log.info("client " + clientPersonal.getName());
        //log.info("id client " + clientPersonal.getIdClient());
        return repository.findById(id)
                .filter(personal ->
                     id.equals(personal.getIdClient())
                )
                .flatMap(personal -> {
                    personal.setDni(clientPersonal.getDni() != null? clientPersonal.getDni() : personal.getDni());
                    personal.setName(clientPersonal.getName() != null? clientPersonal.getName() : personal.getName());
                    personal.setLastName(clientPersonal.getLastName() != null? clientPersonal.getLastName() : personal.getLastName());
                    return repository.save(personal);
                })
                .switchIfEmpty(Mono.just(new PersonalError(HttpStatus.NOT_FOUND, "No se encontro")));
    }

    @Override
    public Mono<ClientPersonal> findById(UUID id) {
        log.info("idRequest " + id);
        return repository
                .findById(id)
                .switchIfEmpty(
                        Mono.just(new PersonalError(HttpStatus.NOT_FOUND, "No se encontro registro"))
                );
    }

    @Override
    public Mono<ClientPersonal> deleteClient(UUID id) {
        return repository.findById(id)
                .flatMap(p ->
                        repository.deleteById(id).thenReturn(p)
                );
    }

    @Override
    public Mono<ClientPersonal> findClientByDni(String dni) {
        log.info("dni "+ dni);
        return repository.findClientByDni(dni)
                .filter(clientPersonal -> dni.equals(clientPersonal.getDni()))
                .switchIfEmpty(Mono.just(new PersonalError(HttpStatus.NOT_FOUND, "No se encontro cliente")));
    }

}
