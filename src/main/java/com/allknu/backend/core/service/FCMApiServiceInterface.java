package com.allknu.backend.core.service;

import com.allknu.backend.web.dto.RequestFCMMessage;
import com.allknu.backend.web.dto.ResponseFcm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface FCMApiServiceInterface {
    void pushToKafkaWebMessage(String email, RequestFCMMessage.Web message);
    void pushToKafkaSubscribeMessage(RequestFCMMessage.Subscribe message);
    Optional<Page<ResponseFcm.Log>> getAllFcmLog(Pageable pageable);
}
