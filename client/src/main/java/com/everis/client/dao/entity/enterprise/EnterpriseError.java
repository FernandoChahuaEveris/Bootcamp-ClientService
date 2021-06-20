package com.everis.client.dao.entity.enterprise;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnterpriseError extends ClientEnterprise{
    private HttpStatus status;
    private String message;
}
