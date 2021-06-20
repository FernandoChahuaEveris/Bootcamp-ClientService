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

import java.util.UUID;

@Slf4j
@Service
public class ClientPersonalServiceImpl implements ClientPersonalService {

    @Autowired
    ClientPersonalRepository<ClientPersonal> clientPersonalRepository;


    @Override
    public Mono createClient(ClientPersonal clientPersonal) {
        return Mono.just(clientPersonal)
                .map(e -> {
                    e.setIdClient(UUID.randomUUID());
                    return e;
                })
                .flatMap(e -> clientPersonalRepository.save(e));
    }

    @Override
    public Flux findAll() {
        return clientPersonalRepository.findAll();
    }

    @Override
    public Mono updateClient(UUID id, ClientPersonal clientPersonal) {

        log.info("client " + clientPersonal.getName());
        log.info("id client " + clientPersonal.getIdClient());
        return clientPersonalRepository.findById(id)
                .filter(element -> element.getIdClient().equals(clientPersonal.getIdClient()))
                .switchIfEmpty(Mono.error(new Exception("No se encontro")))
                .map(e -> clientPersonal)
                .flatMap(e -> clientPersonalRepository.save(e));
    }

    @Override
    public Mono findById(UUID id) {
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
    public Mono deleteClient(UUID id) {
        return clientPersonalRepository.findById(id)
                .flatMap(p ->
                        clientPersonalRepository.deleteById(id).thenReturn(p)
                );
    }

}
