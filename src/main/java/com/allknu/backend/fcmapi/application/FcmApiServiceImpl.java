package com.allknu.backend.fcmapi.application;

import com.allknu.backend.fcmapi.application.dto.PushMobileRequestDto;
import com.allknu.backend.fcmapi.application.dto.SubscribeRequestDto;
import com.allknu.backend.fcmapi.domain.FirebaseLog;
import com.allknu.backend.fcmapi.domain.PushRepository;
import com.allknu.backend.fcmapi.domain.SubscribeType;
import com.allknu.backend.fcmapi.domain.FirebaseLogRepository;
import com.allknu.backend.fcmapi.application.dto.ResponseFcm;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class FcmApiServiceImpl implements FcmApiService {
    private static final Logger log = LoggerFactory.getLogger(FcmApiServiceImpl.class);

    private final FirebaseLogRepository firebaseLogRepository;
    private final PushRepository pushRepository;

    @Override
    @Transactional
    public void pushMobileMessage(String email, PushMobileRequestDto requestDto) {
        //push
        pushRepository.pushToTopicsRequest(requestDto.toPushToTopicsRequestDto());

        FirebaseLog log = FirebaseLog.builder()
                .adminEmail(email)
                .title(requestDto.getTitle())
                .body(requestDto.getBody())
                .link(requestDto.getClickLink())
                .build();

        firebaseLogRepository.save(log);
        FcmApiServiceImpl.log.info("fcm 푸쉬 로그 기록, 전송자: " + email);
    }

    @Override
    public void pushSubscribeMessage(SubscribeRequestDto requestDto) {
        pushRepository.updateSubscribeTopicsRequest(requestDto.toUpdateSubscribeTopicsRequestDto());
    }

    @Transactional
    @Override
    public Page<ResponseFcm.Log> getAllFcmLog(Pageable pageable) {
        Page<FirebaseLog> logs = firebaseLogRepository.findAll(pageable);
        return logs.map(ResponseFcm.Log::of);
    }

    @Override
    public List<ResponseFcm.SubscribeType> getAllKnuSubscribeTypes(String team) {
        List<ResponseFcm.SubscribeType> list = new LinkedList<>();
        EnumSet.allOf(SubscribeType.class)
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
