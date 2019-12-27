package com.example.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    @KafkaListener(topics = "test", groupId = "group-test")
    public void listener(String message, Acknowledgment acknowledgment) {
        System.out.println(message);
//        int i = 1/0;
        acknowledgment.acknowledge();
    }
}
