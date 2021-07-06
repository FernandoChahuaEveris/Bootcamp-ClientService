package com.everis.client.controller;

import com.everis.client.dao.entity.enterprise.ClientEnterprise;
import com.everis.client.service.enterprise.ClientEnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/client/enterprise")
public class ClientEnterpriseController {

    @Autowired
    ClientEnterpriseService<ClientEnterprise> service;

    @GetMapping
    public Flux<ClientEnterprise> getClients(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity> findById(@PathVariable UUID id){
        return service.findById(id).map(clientEnterprise -> {
            if(clientEnterprise.getClass() == ClientEnterprise.class){
                return ResponseEntity.status(HttpStatus.OK).body(clientEnterprise);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(clientEnterprise);
            }
        });
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity> createClient(@RequestBody ClientEnterprise clientEnterprise){
        return service.createClient(clientEnterprise).map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<ResponseEntity> deleteClient(@PathVariable UUID id){
        return service.deleteClient(id).map(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity> updateClient(@PathVariable("id") UUID id, @RequestBody ClientEnterprise clientEnterprise){
        return service.updateClient(id, clientEnterprise).map(ResponseEntity::ok);
    }

    @PostMapping("/pyme/{ruc}")
    public Mono<ResponseEntity> assignPyme(@PathVariable("ruc")String ruc){
        return service.assignClientPyme(ruc).map(ResponseEntity::ok);
    }
}
