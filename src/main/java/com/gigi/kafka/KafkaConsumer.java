package com.gigi.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "my_topic", groupId = "kedaya_group")
    public void listen(String message) {
        System.out.println("Received message: " + message);

    }
}
