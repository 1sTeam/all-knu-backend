package com.allknu.backend.provider.service;

import com.allknu.backend.core.service.FCMApiServiceInterface;
import com.allknu.backend.kafka.MessageProducer;
import com.allknu.backend.kafka.dto.FCMSubscribeMessage;
import com.allknu.backend.kafka.dto.FCMWebMessage;
import com.allknu.backend.web.dto.RequestFCMMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FCMApiService implements FCMApiServiceInterface {
    private final MessageProducer messageProducer; // FCM 마이크로서비스로 보내기 위한 카프카 프로듀서

    @Override
    public void pushToKafkaWebMessage(RequestFCMMessage.Web message) {
        FCMWebMessage fcmWebMessage = message.toFCMRequestMessage();
        messageProducer.sendFCMMessage(fcmWebMessage);
    }

    @Override
    public void pushToKafkaSubscribeMessage(RequestFCMMessage.Subscribe message) {
        FCMSubscribeMessage fcmSubscribeMessage = message.toFCMSubscribeMessage();
        messageProducer.sendFCMRequestSubscribeMessage(fcmSubscribeMessage);
    }
}
