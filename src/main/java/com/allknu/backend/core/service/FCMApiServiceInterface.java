package com.allknu.backend.core.service;

import com.allknu.backend.web.dto.RequestFCMMessage;

public interface FCMApiServiceInterface {
    void pushToKafkaWebMessage(RequestFCMMessage.Web message);
    void pushToKafkaSubscribeMessage(RequestFCMMessage.Subscribe message);
}
