package com.everis.client.service.enterprise;

import com.everis.client.dao.entity.CreditCard;
import com.everis.client.dao.entity.cusexceptions.NotFoundException;
import com.everis.client.dao.entity.enterprise.ClientEnterprise;
import com.everis.client.dao.entity.enterprise.EnterpriseError;
import com.everis.client.dao.entity.personal.ClientPersonal;
import com.everis.client.dao.entity.personal.PersonalError;
import com.everis.client.dao.repository.enterprise.ClientEnterpriseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class ClientEnterpriseServiceImpl implements ClientEnterpriseService<ClientEnterprise> {

    @Autowired
    ClientEnterpriseRepository<ClientEnterprise> repository;
    @Autowired
    WebClient.Builder builder;

    boolean isExist;

    @Override
    public Mono<ClientEnterprise> createClient(ClientEnterprise client) {
        String id = UUID.randomUUID().toString();
        return findClientByRuc(client.getRuc()).doOnNext(clientEnterprise -> {
            isExist = true;
        }).switchIfEmpty(Mono.just(client)
                .flatMap(enterprise -> {
                    enterprise.setIdClient(UUID.fromString(id));
                    enterprise.setTypeClient("Empresarial");
                    enterprise.setCreationDate(new Date());

                    return repository.save(enterprise);
                }));
    }

    @Override
    public Flux<ClientEnterprise> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<ClientEnterprise> updateClient(UUID id, ClientEnterprise clientEnterprise) {
        return repository.findById(id)
                .filter(enterprise ->
                        id.equals(enterprise.getIdClient())
                )
                .flatMap(enterprise -> {
                    enterprise.setRuc(clientEnterprise.getRuc() != null ? clientEnterprise.getRuc() : enterprise.getRuc());
                    enterprise.setBusinessName(clientEnterprise.getBusinessName() != null ? clientEnterprise.getBusinessName() : enterprise.getBusinessName());
                    return repository.save(enterprise);
                })
                .switchIfEmpty(Mono.just(new EnterpriseError(HttpStatus.NOT_FOUND, "No se encontro")));
    }

    @Override
    public Mono<ClientEnterprise> findById(UUID id) {
        return repository
                .findById(id)
                .switchIfEmpty(
                        Mono.just(new EnterpriseError(HttpStatus.NOT_FOUND, "No se encontro registro"))
                );
    }

    @Override
    public Mono<ClientEnterprise> deleteClient(UUID id) {
        return repository.findById(id)
                .flatMap(p ->
                        repository.deleteById(id).thenReturn(p)
                );
    }

    @Override
    public Mono<ClientEnterprise> assignClientPyme(String ruc) {
        boolean isValid = false;

        Mono<ClientEnterprise> monoClient = findClientByRuc(ruc);
        Mono<CreditCard> monoCreditCard = builder.build()
                .get()
                .uri("localhost:8085/creditcard/")
                .retrieve()
                .bodyToMono(CreditCard.class);

        monoCreditCard.doOnNext(creditCard -> {
            System.out.println("Validar si es apto para crear perfil VIP");
        });
        monoClient.filter(clientEnterprise -> !ruc.equals(clientEnterprise.getRuc()))
                .onErrorResume(throwable -> Mono.just(new EnterpriseError(HttpStatus.NOT_FOUND, "No se puede encontrar cliente")));

        return repository.findById(monoClient.block().getIdClient())
                .flatMap(clientEnterprise -> {
                    clientEnterprise.setProfile("VIP");
                    return repository.save(clientEnterprise);
                });
    }

    @Override
    public Mono<ClientEnterprise> findClientByRuc(String ruc) {
        log.info("Ruc " + ruc);
        return repository.findClientByRuc(ruc)
                .filter(clientEnterprise -> ruc.equals(clientEnterprise.getRuc()))
                .doOnError(throwable -> new NotFoundException("Error al ubicar cliente"));
    }
}
