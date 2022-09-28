package com.allknu.backend.provider.service;

import com.allknu.backend.core.service.FCMApiService;
import com.allknu.backend.domain.FirebaseLog;
import com.allknu.backend.kafka.MessageProducer;
import com.allknu.backend.kafka.dto.FCMMobileMessage;
import com.allknu.backend.kafka.dto.FCMSubscribeMessage;
import com.allknu.backend.kafka.dto.FCMWebMessage;
import com.allknu.backend.repository.FirebaseLogRepository;
import com.allknu.backend.web.dto.RequestFCMMessage;
import com.allknu.backend.web.dto.ResponseFcm;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/*
 * 관리자페이지 기능 -> 마이크로서비스 분리 대상
 */
@Service
@RequiredArgsConstructor
public class FCMApiServiceImpl implements FCMApiService {
    private static final Logger log = LoggerFactory.getLogger(FCMApiServiceImpl.class);

    private final MessageProducer messageProducer; // FCM 마이크로서비스로 보내기 위한 카프카 프로듀서
    private final FirebaseLogRepository firebaseLogRepository;

    @Deprecated
    @Transactional
    @Override
    public void pushToKafkaWebMessage(String email, FCMWebMessage message) {
        //push
        messageProducer.sendFCMWebMessage(message);

        //log 등록
        FirebaseLog log = FirebaseLog.builder()
                .adminEmail(email)
                .title(message.getTitle())
                .body(message.getBody())
                .link(message.getClickLink())
                .build();
        firebaseLogRepository.save(log);
        FCMApiServiceImpl.log.info("fcm 푸쉬 로그 기록, 전송자: " + email);
    }

    @Transactional
    @Override
    public void pushToKafkaMobileMessage(String email, FCMMobileMessage message) {
        //push
        messageProducer.sendFCMMobileMessage(message);

        //log 등록
        FirebaseLog log = FirebaseLog.builder()
                .adminEmail(email)
                .title(message.getTitle())
                .body(message.getBody())
                .link(message.getClickLink())
                .build();
        firebaseLogRepository.save(log);
        FCMApiServiceImpl.log.info("fcm 푸쉬 로그 기록, 전송자: " + email);
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
