package com.allknu.backend.fcmapi.presentation;


import com.allknu.backend.fcmapi.application.FCMApiService;
import com.allknu.backend.fcmapi.domain.dto.FCMWebMessage;
import com.allknu.backend.fcmapi.domain.dto.MLRequestMessage;
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
public class FcmConsumer {
    private static final Logger logger = LoggerFactory.getLogger(FcmConsumer.class);
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
