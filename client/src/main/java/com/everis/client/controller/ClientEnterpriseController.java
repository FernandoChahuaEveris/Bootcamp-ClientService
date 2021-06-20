package com.everis.client.controller;

import com.everis.client.dao.entity.enterprise.ClientEnterprise;
import com.everis.client.dao.entity.personal.ClientPersonal;
import com.everis.client.service.enterprise.ClientEnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/client/enterprise")
public class ClientEnterpriseController {

    @Autowired
    ClientEnterpriseService<ClientEnterprise> service;

    @GetMapping
    public Flux<ResponseEntity> getClients(){
        return service.findAll().map(ResponseEntity::ok);
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
}
