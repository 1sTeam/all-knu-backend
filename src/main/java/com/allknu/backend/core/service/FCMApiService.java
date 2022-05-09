package com.allknu.backend.core.service;

import com.allknu.backend.kafka.dto.FCMMobileMessage;
import com.allknu.backend.kafka.dto.FCMWebMessage;
import com.allknu.backend.web.dto.RequestFCMMessage;
import com.allknu.backend.web.dto.ResponseFcm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface FCMApiService {
    void pushToKafkaWebMessage(String email, FCMWebMessage message);
    void pushToKafkaMobileMessage(String email, FCMMobileMessage message);
    void pushToKafkaSubscribeMessage(RequestFCMMessage.Subscribe message);
    Optional<Page<ResponseFcm.Log>> getAllFcmLog(Pageable pageable);
    List<ResponseFcm.SubscribeType> getAllKnuSubscribeTypes(String team);
}