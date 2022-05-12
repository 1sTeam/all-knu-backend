package com.allknu.backend.kafka;

import com.allknu.backend.kafka.dto.FCMMobileMessage;
import com.allknu.backend.kafka.dto.FCMSubscribeMessage;
import com.allknu.backend.kafka.dto.FCMWebMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MessageProducer {
    private static final Logger logger = LoggerFactory.getLogger(MessageProducer.class);
    private static final String FCM_TOPIC = "fcm";
    private static final String FCM_SUBSCRIBE_TOPIC = "fcmSubscribe";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Deprecated
    public void sendFCMWebMessage(FCMWebMessage fcmWebMessage) {
        logger.info("Produce message : success");
        this.kafkaTemplate.send(FCM_TOPIC, fcmWebMessage);
    }

    public void sendFCMMobileMessage(FCMMobileMessage fcmMobileMessage) {
        logger.info("Produce message : success");
        this.kafkaTemplate.send(FCM_TOPIC, fcmMobileMessage);
    }

    public void sendFCMRequestSubscribeMessage(FCMSubscribeMessage message) {
        logger.info("Produce message : success");
        this.kafkaTemplate.send(FCM_SUBSCRIBE_TOPIC, message);
    }
}