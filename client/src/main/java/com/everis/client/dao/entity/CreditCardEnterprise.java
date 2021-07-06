package com.everis.client.dao.entity;

import com.everis.client.dao.entity.enterprise.ClientEnterprise;
import com.everis.client.dao.entity.personal.ClientPersonal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.UUID;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditCardEnterprise {
    @Id
    private UUID idPersonalCreditCard;
    private ClientEnterprise businessClient;
    private BigDecimal creditBalance;
    private String currencyType;
    private boolean state;
}
