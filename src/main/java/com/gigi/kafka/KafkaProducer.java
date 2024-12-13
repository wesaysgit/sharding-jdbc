package com.gigi.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Service
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String message) {
        Long nTime = System.currentTimeMillis();
        kafkaTemplate.send(topic, message).addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.info("kafka消息->发送失败{topic:" + topic + ", msg:" + message + "}：" + throwable.getMessage() +
                        ", 耗时(ms):" + (System.currentTimeMillis() - nTime), throwable);
            }

            @Override
            public void onSuccess(SendResult<String, String> stringStringSendResult) {

            }
        });
    }
}
