package com.everis.client.service.enterprise;

import com.everis.client.dao.entity.ClientEnterprise;
import com.everis.client.dao.repository.enterprise.ClientEnterpriseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public class ClientEnterpriseServiceImpl implements ClientEnterpriseService{

    @Autowired
    ClientEnterpriseRepository<ClientEnterprise> enterpriseRepository;

    @Override
    public Mono createClient(ClientEnterprise clientPersonal) {
        return null;
    }

    @Override
    public Flux findAll() {
        return null;
    }

    @Override
    public Mono updateClient(UUID id, ClientEnterprise clientPersonal) {
        return null;
    }

    @Override
    public Mono findById(UUID id) {
        return null;
    }

    @Override
    public Mono deleteClient(UUID id) {
        return null;
    }
}
