package com.everis.client.dao.repository.enterprise;

import com.everis.client.dao.entity.enterprise.ClientEnterprise;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientEnterpriseRepository<T extends ClientEnterprise> extends ReactiveMongoRepository<T, UUID> {
}
