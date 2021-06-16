package com.everis.client.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    private UUID idClient;
    private String name;
    private String typeClient;
}
