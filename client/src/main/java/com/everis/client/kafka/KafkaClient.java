package com.everis.client.kafka;

import org.springframework.kafka.core.KafkaTemplate;

public class KafkaClient<T, V> {

    private KafkaTemplate<T, V> kafkaTemplate;

    private KafkaTemplate<T, V> getKafka(){
        return kafkaTemplate;
    }
}
