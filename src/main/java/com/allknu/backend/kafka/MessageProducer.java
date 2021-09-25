package com.allknu.backend.kafka;

import com.allknu.backend.kafka.dto.FCMWebMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MessageProducer {
    private static final String FCM_TOPIC = "fcm";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendFCMMessage(FCMWebMessage fcmWebMessage) {

        System.out.println(String.format("Produce message : success" ));
        this.kafkaTemplate.send(FCM_TOPIC, fcmWebMessage);
    }
}