package com.example.kafka;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.zip.DeflaterOutputStream;

@Component
public class MessageProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void sendMessage(int i) {
        ListenableFuture<SendResult> result = kafkaTemplate.send("test", "hello world" + i);
        result.addCallback(new ListenableFutureCallback<SendResult>() {
            @Override
            public void onFailure(Throwable throwable) {
                System.err.println(throwable);
            }

            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println(sendResult);
            }
        });

//        ProducerRecord producerRecord = new ProducerRecord("test1", new User("pengfei"));
//        ListenableFuture<SendResult> result1 = kafkaTemplate.send(producerRecord);
//        result1.addCallback(new ListenableFutureCallback<SendResult>() {
//            @Override
//            public void onFailure(Throwable throwable) {
//                System.err.println(throwable);
//            }
//
//            @Override
//            public void onSuccess(SendResult sendResult) {
//                System.out.println(sendResult);
//            }
//        });
    }
}
