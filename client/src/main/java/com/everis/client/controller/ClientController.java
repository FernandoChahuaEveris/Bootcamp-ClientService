package com.everis.client.controller;

import com.everis.client.dao.entity.Client;
import com.everis.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    ClientService clientService;

    @PostMapping
    public Mono<Client> createClient(@RequestBody Client client){
        return clientService.createClient(client);
    }

    @GetMapping
    public Flux<Client> getClients(){
        return clientService.findAll();
    }

    @PutMapping("/{id}")
    public Mono<Client> updateClient(@PathVariable("id") UUID id, @RequestBody Client client){
        return clientService.updateClient(id, client);
    }

    @DeleteMapping("/{id}")
    public Mono<Client> deleteClientById(@PathVariable("id") UUID id){
        return clientService.deleteClient(id);
    }

    @GetMapping("/{id}")
    public Mono<Client> findById(@PathVariable("id") UUID id){
        return clientService.findById(id);
    }
}
