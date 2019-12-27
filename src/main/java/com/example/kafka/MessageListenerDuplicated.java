package com.example.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@Component
@Slf4j
public class MessageListenerDuplicated implements ConsumerSeekAware {

    @KafkaListener(topics = "test", groupId = "group-test2")
    public void listener(String message, Acknowledgment acknowledgment, @Header(KafkaHeaders.OFFSET) int offset) {
        System.out.println("dup:" + message);
        System.out.println(offset);
        acknowledgment.acknowledge();
    }

    private final ThreadLocal<ConsumerSeekCallback> callbackForThread = new ThreadLocal<>();

    private final Map<TopicPartition, ConsumerSeekCallback> callbacks = new ConcurrentHashMap<>();

    @Override
    public void registerSeekCallback(ConsumerSeekCallback callback) {
        this.callbackForThread.set(callback);
    }

    @Override
    public void onPartitionsAssigned(Map<org.apache.kafka.common.TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
        assignments.keySet().forEach(tp -> this.callbacks.put(tp, this.callbackForThread.get()));
    }

    @Override
    public void onPartitionsRevoked(Collection<org.apache.kafka.common.TopicPartition> partitions) {
        partitions.forEach(tp -> this.callbacks.remove(tp));
        this.callbackForThread.remove();
    }

    @Override
    public void onIdleContainer(Map<org.apache.kafka.common.TopicPartition, Long> assignments, ConsumerSeekCallback callback) {

    }

    public void seekToStart() {
        System.out.println("cccccc" + this.callbacks);
        this.callbacks.forEach((tp, callback) -> {
            System.out.println("bbbbbb" + tp.topic());
            callback.seekToBeginning(tp.topic(), tp.partition());
        });
    }
}
