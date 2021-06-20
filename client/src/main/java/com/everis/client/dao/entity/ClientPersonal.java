package com.everis.client.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Data
@Document
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientPersonal extends DataResponse{
    @Id
    private UUID idClient;
    private String name;
    private String lastName;
    private String dni;
    private Date creationDate;
    private String typeClient;
}
