package com.everis.client.service.personal;

import com.everis.client.dao.entity.ClientPersonal;
import com.everis.client.dao.entity.ErrorResponse;
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
    ClientPersonalRepository<ClientPersonal> clientPersonalRepository;


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
                .flatMap(personal -> clientPersonalRepository.save(personal));
    }

    @Override
    public Flux<ClientPersonal> findAll() {
        return clientPersonalRepository.findAll();
    }

    @Override
    public Mono<ClientPersonal> updateClient(UUID id, ClientPersonal clientPersonal) {

        log.info("client " + clientPersonal.getName());
        //log.info("id client " + clientPersonal.getIdClient());
        return clientPersonalRepository.findById(id)
                .filter(personal ->
                     id.equals(personal.getIdClient())
                )
                .flatMap(personal -> {
                    personal.setDni(clientPersonal.getDni() != null? clientPersonal.getDni() : personal.getDni());
                    personal.setName(clientPersonal.getName() != null? clientPersonal.getName() : personal.getName());
                    personal.setLastName(clientPersonal.getLastName() != null? clientPersonal.getLastName() : personal.getLastName());
                    return clientPersonalRepository.save(personal);
                })
                .switchIfEmpty(Mono.just(new ErrorResponse(HttpStatus.NOT_FOUND, "No se encontro")));
    }

    @Override
    public Mono<ClientPersonal> findById(UUID id) {
        log.info("idRequest " + id);
        return clientPersonalRepository
                .findById(id)
                .switchIfEmpty(
                        Mono.just(new ErrorResponse(HttpStatus.NOT_FOUND, "No se encontro registro"))
                );
                /*.map(ResponseEntity::ok)
                .cast(ResponseEntity.class)
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND, "No se encontro")));*/
    }

    @Override
    public Mono<ClientPersonal> deleteClient(UUID id) {
        return clientPersonalRepository.findById(id)
                .flatMap(p ->
                        clientPersonalRepository.deleteById(id).thenReturn(p)
                );
    }

}
