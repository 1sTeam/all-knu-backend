package com.allknu.backend.kafka;


import com.allknu.backend.core.service.FCMApiService;
import com.allknu.backend.kafka.dto.FCMWebMessage;
import com.allknu.backend.kafka.dto.MLRequestMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MessageConsumer {
    private static final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);
    private final FCMApiService fcmApiService;

    @KafkaListener(topics = "mlRequest", groupId = "all-knu-backend", containerFactory = "MLRequestMessageListener")
    public void consume(@Payload MLRequestMessage message, @Headers MessageHeaders headers) throws IOException {
        logger.info("Consumed message : " + message.getTitle());
        FCMWebMessage fcmWebMessage = FCMWebMessage.builder()
                .title(message.getTitle())
                .clickLink(message.getClickLink())
                .body("비교과 프로그램 안내")
                .subscribeTypes(message.getSubscribeTypes())
                .build();

        fcmApiService.pushToKafkaWebMessage("auto_by_flask", fcmWebMessage);
    }
}
