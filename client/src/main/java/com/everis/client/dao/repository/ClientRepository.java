package com.everis.client.dao.repository;

import com.everis.client.dao.entity.Client;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientRepository extends ReactiveMongoRepository<Client, UUID> {
}
