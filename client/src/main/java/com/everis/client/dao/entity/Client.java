package com.everis.client.dao.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document
public class Client {
    private UUID idClient;
    private String name;
    private String typeClient;
}
