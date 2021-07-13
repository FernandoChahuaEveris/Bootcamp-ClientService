package com.everis.client.service.personal.topic;


import com.everis.client.dao.entity.CreditCardPersonal;
import com.everis.client.service.personal.ClientPersonalService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import reactor.core.publisher.Flux;

@Slf4j
@Component
public class TopicCreditCard {

    private KafkaTemplate<String, Object> kafkaTemplate;

    public TopicCreditCard(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public String sendDniClient(String dni) {
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send("t-credits-clients", dni);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(@NotNull Throwable ex) {
                ex.printStackTrace();
            }

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                log.info("Result " + result.getRecordMetadata());
            }
        });
        return "";
    }

    @KafkaListener(topics = "t-return-credit-card", groupId = "group_credit_card_personal", containerFactory = "personalCreditCardContainerFactory")
    public void receiverCreditCardPersonal(Flux<CreditCardPersonal> creditCardPersonal) {

    }
}
