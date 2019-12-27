package com.example.kafka;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component
public class MessageRunner implements CommandLineRunner {

    @Autowired
    private MessageProducer messageProducer;

    @Autowired
    private MessageListenerDuplicated messageListenerDuplicated;

    @Override
    public void run(String... args) throws Exception {
        IntStream.rangeClosed(1, 10).forEach(i -> messageProducer.sendMessage(i));
//        Stream.(1,2,3,4,5,6,7,8,9,10).forEach(i -> messageProducer.sendMessage(i));
//        messageProducer.sendMessage(1);

        messageListenerDuplicated.seekToStart();
    }


}
