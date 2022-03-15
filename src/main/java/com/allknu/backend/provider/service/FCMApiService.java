package com.allknu.backend.provider.service;

import com.allknu.backend.core.service.FCMApiServiceInterface;
import com.allknu.backend.entity.FirebaseLog;
import com.allknu.backend.kafka.MessageProducer;
import com.allknu.backend.kafka.dto.FCMSubscribeMessage;
import com.allknu.backend.kafka.dto.FCMWebMessage;
import com.allknu.backend.repository.FirebaseLogRepository;
import com.allknu.backend.web.dto.RequestFCMMessage;
import com.allknu.backend.web.dto.ResponseFcm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class FCMApiService implements FCMApiServiceInterface {
    private final MessageProducer messageProducer; // FCM 마이크로서비스로 보내기 위한 카프카 프로듀서
    private final FirebaseLogRepository firebaseLogRepository;

    @Transactional
    @Override
    public void pushToKafkaWebMessage(String email, FCMWebMessage message) {
        //push
        messageProducer.sendFCMMessage(message);

        //log 등록
        FirebaseLog log = FirebaseLog.builder()
                .adminEmail(email)
                .title(message.getTitle())
                .body(message.getBody())
                .link(message.getClickLink())
                .build();
        firebaseLogRepository.save(log);
    }

    @Override
    public void pushToKafkaSubscribeMessage(RequestFCMMessage.Subscribe message) {
        FCMSubscribeMessage fcmSubscribeMessage = message.toFCMSubscribeMessage();
        messageProducer.sendFCMRequestSubscribeMessage(fcmSubscribeMessage);
    }

    @Transactional
    @Override
    public Optional<Page<ResponseFcm.Log>> getAllFcmLog(Pageable pageable) {
        Page<FirebaseLog> logs = firebaseLogRepository.findAll(pageable);
        return Optional.ofNullable(logs.map(ResponseFcm.Log::of));
    }

    @Override
    public List<ResponseFcm.SubscribeType> getAllKnuSubscribeTypes(String team) {
        List<ResponseFcm.SubscribeType> list = new LinkedList<>();
        EnumSet.allOf(com.allknu.backend.core.types.SubscribeType.class)
                .forEach(type -> {
                    if(team == null || type.getTeam().equals(team)) {
                        ResponseFcm.SubscribeType responseDto =
                                ResponseFcm.SubscribeType
                                        .builder()
                                        .topic(type.toString())
                                        .korean(type.getKorean())
                                        .team(type.getTeam())
                                .build();
                        list.add(responseDto);
                    }
                });
        return list;
    }
}
