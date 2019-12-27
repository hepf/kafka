package com.example.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener1 {

    @KafkaListener(topics = "test1", groupId = "test-group1")
    public void listener(User user) {
        System.out.println(user.getName());
    }
}
