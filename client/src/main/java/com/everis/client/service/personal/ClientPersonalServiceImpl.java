package com.everis.client.service.personal;

import com.everis.client.dao.entity.CreditCardPersonal;
import com.everis.client.dao.entity.cusexceptions.NotFoundException;
import com.everis.client.dao.entity.personal.ClientPersonal;
import com.everis.client.dao.entity.personal.PersonalError;
import com.everis.client.dao.repository.personal.ClientPersonalRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class ClientPersonalServiceImpl implements ClientPersonalService<ClientPersonal> {

    @Autowired
    ClientPersonalRepository<ClientPersonal> repository;

    @Autowired
    WebClient.Builder builder;

    @Value("${credit.hostname.uri}")
    String creditUri;

    boolean isExist;


    @Override
    public Mono<ClientPersonal> createClient(ClientPersonal client) {
        String id = UUID.randomUUID().toString();

        return findClientByDni(client.getDni()).doOnNext(clientPersonal -> {
            isExist = true;
        }).switchIfEmpty(Mono.just(client).flatMap(clientPersonal -> {
            clientPersonal.setIdClient(UUID.fromString(id));
            clientPersonal.setTypeClient("Personal");
            clientPersonal.setCreationDate(new Date());
            clientPersonal.setProfile("");
            return repository.save(clientPersonal);
        }));
    }

    @Override
    @HystrixCommand(fallbackMethod = "findAllDefault")
    public Flux<ClientPersonal> findAll() {
        return repository.findAll();
    }

    public Flux<ClientPersonal> findAllDefault() {
        return Flux.empty();
    }

    @Override
    public Mono<ClientPersonal> updateClient(UUID id, ClientPersonal clientPersonal) {

        log.info("client " + clientPersonal.getName());
        //log.info("id client " + clientPersonal.getIdClient());
        return repository.findById(id)
                .filter(personal ->
                        id.equals(personal.getIdClient())
                )
                .flatMap(personal -> {
                    personal.setDni(clientPersonal.getDni() != null ? clientPersonal.getDni() : personal.getDni());
                    personal.setName(clientPersonal.getName() != null ? clientPersonal.getName() : personal.getName());
                    personal.setLastName(clientPersonal.getLastName() != null ? clientPersonal.getLastName() : personal.getLastName());
                    return repository.save(personal);
                })
                .switchIfEmpty(Mono.just(new PersonalError(HttpStatus.NOT_FOUND, "No se encontro")));
    }

    @Override
    @HystrixCommand(fallbackMethod = "findAllDefault")
    public Mono<ClientPersonal> findById(UUID id) {
        log.info("idRequest " + id);
        return repository
                .findById(id)
                .switchIfEmpty(
                        Mono.just(new PersonalError(HttpStatus.NOT_FOUND, "No se encontro registro"))
                );
    }

    @Override
    public Mono<ClientPersonal> deleteClient(UUID id) {
        return repository.findById(id)
                .flatMap(p ->
                        repository.deleteById(id).thenReturn(p)
                );
    }

    @Override
    public Mono<ClientPersonal> findClientByDni(String dni) {
        log.info("dni " + dni);
        return repository
                .findClientByDni(dni).filter(clientPersonal -> dni.equals(clientPersonal.getDni()))
                .doOnError(throwable -> new NotFoundException("No se encontro", throwable));
    }

    @Override
    public Mono<ClientPersonal> assignClientVip(String dni) {

        return findClientByDni(dni).flatMap(clientPersonal -> {

            Flux<CreditCardPersonal> monoCreditCard = builder.build()
                    .get()
                    .uri(creditUri +"credits-loans/personal-credit-card/dni/"+ dni)
                    .retrieve()
                    .bodyToFlux(CreditCardPersonal.class);

            log.info("Obtener tarjeta de credito URI " + creditUri +"credits-loans/personal-credit-card/dni/"+ dni);
            long cant = monoCreditCard.toStream().filter(creditCardPersonal -> creditCardPersonal.getPersonalClient().getDni().equals(clientPersonal.getDni()))
                    .count();
            log.info("Cantidad " + cant);

            if(cant == 0){
               return Mono.error(new NotFoundException("No se encontro tarjeta de credito asociada"));
               //return null;
            }
            clientPersonal.setProfile("VIP");
            log.info("Antes de guardar");

            return Mono.just(clientPersonal).flatMap(repository::save);
        });
    }

}
