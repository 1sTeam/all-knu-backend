package com.allknu.backend.fcmapi.domain;

import com.allknu.backend.fcmapi.domain.dto.FCMMobileMessage;
import com.allknu.backend.fcmapi.domain.dto.FCMSubscribeMessage;
import com.allknu.backend.fcmapi.domain.dto.FCMWebMessage;

public interface FcmProducer {

    void sendFCMWebMessage(FCMWebMessage fcmWebMessage);
    void sendFCMMobileMessage(FCMMobileMessage fcmMobileMessage);
    void sendFCMRequestSubscribeMessage(FCMSubscribeMessage message);
}
