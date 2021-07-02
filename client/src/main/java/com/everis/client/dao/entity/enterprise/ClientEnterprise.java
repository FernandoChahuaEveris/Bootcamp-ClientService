package com.everis.client.dao.entity.enterprise;

import com.everis.client.dao.entity.DataResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientEnterprise extends DataResponse {

    @Id
    private UUID idClient;
    private String typeClient;
    private String businessName;
    private String ruc;
    private Date creationDate;
    private String profile;
}
