package com.everis.client.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientEnterprise extends DataResponse{
    private String typeClient;
    private String businessName;
    private String ruc;
    private Date creationDate;
    @Id
    private UUID idClient;
}
