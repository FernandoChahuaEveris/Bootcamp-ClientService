package com.everis.client.controller;

import com.everis.client.dao.entity.personal.ClientPersonal;
import com.everis.client.service.personal.ClientPersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/client/personal")
public class ClientPersonalController {

    @Autowired
    ClientPersonalService<ClientPersonal> service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity> createClient(@RequestBody ClientPersonal clientPersonal){
        return service.createClient(clientPersonal).map(ResponseEntity::ok);
    }

    @GetMapping
    public Flux<ClientPersonal> getClients(){
        return service.findAll();
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity> updateClient(@PathVariable("id") UUID id, @RequestBody ClientPersonal clientPersonal){
        return service.updateClient(id, clientPersonal).map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<ClientPersonal> deleteClientById(@PathVariable("id") UUID id){
        return service.deleteClient(id);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity> findById(@PathVariable("id") UUID id){
        return service.findById(id).map(clientPersonal -> {
                if(clientPersonal.getClass() == ClientPersonal.class){
                    return ResponseEntity.status(HttpStatus.OK).body(clientPersonal);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(clientPersonal);
                }
        });
    }

    @GetMapping("/dni/{dni}")
    public Mono<ResponseEntity> findClientByDni(@PathVariable("dni") String dni){
        return service.findClientByDni(dni)
                .map(clientPersonal -> {
                    if(clientPersonal.getClass() == ClientPersonal.class){
                        return ResponseEntity.status(HttpStatus.OK).body(clientPersonal);
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(clientPersonal);
                    }
                });
    }

    @PostMapping("/vip/{dni}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<ResponseEntity> assignVip(@PathVariable("dni")String dni){
        return service.assignClientVip(dni).map(ResponseEntity::ok);
    }
}
