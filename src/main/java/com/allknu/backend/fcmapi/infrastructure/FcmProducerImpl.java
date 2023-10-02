package com.allknu.backend.fcmapi.infrastructure;

import com.allknu.backend.fcmapi.domain.FcmProducer;
import com.allknu.backend.fcmapi.domain.dto.FCMMobileMessage;
import com.allknu.backend.fcmapi.domain.dto.FCMSubscribeMessage;
import com.allknu.backend.fcmapi.domain.dto.FCMWebMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FcmProducerImpl implements FcmProducer {
    private static final Logger logger = LoggerFactory.getLogger(FcmProducerImpl.class);
    private static final String FCM_TOPIC = "fcm";
    private static final String FCM_SUBSCRIBE_TOPIC = "fcmSubscribe";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Deprecated
    @Override
    public void sendFCMWebMessage(FCMWebMessage fcmWebMessage) {
        logger.info("Produce message : success");
        this.kafkaTemplate.send(FCM_TOPIC, fcmWebMessage);
    }

    @Override
    public void sendFCMMobileMessage(FCMMobileMessage fcmMobileMessage) {
        logger.info("Produce message : success");
        this.kafkaTemplate.send(FCM_TOPIC, fcmMobileMessage);
    }

    @Override
    public void sendFCMRequestSubscribeMessage(FCMSubscribeMessage message) {
        logger.info("Produce message : success");
        this.kafkaTemplate.send(FCM_SUBSCRIBE_TOPIC, message);
    }
}