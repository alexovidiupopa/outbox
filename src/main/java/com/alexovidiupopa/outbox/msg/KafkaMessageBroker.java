package com.alexovidiupopa.outbox.msg;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaMessageBroker implements MessageBroker {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaMessageBroker(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(String topic, String message) {
        //kafkaTemplate.send(topic, message); < - we would do actual sends here
        log.atInfo().log(String.format("Sending %s on topic %s", message, topic));
    }
}