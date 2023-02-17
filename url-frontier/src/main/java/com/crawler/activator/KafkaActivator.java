package com.crawler.activator;

import com.crawler.entity.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * author  danial
 * email  doneskandari@gmail.com
 */
@Component
public class KafkaActivator {

    @Value("${kafka.topic.name}")
    private String topic;

    private final KafkaTemplate<String, Request> kafkaTemplate;

    public KafkaActivator(KafkaTemplate<String, Request> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void push(@Payload Request request) {
        kafkaTemplate.send(topic, request);
    }
}
