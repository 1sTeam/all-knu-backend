package com.allknu.backend.provider.service;

import com.allknu.backend.core.types.SubscribeType;
import com.allknu.backend.web.dto.RequestFCMMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;

@SpringBootTest
@ActiveProfiles("local")
public class FCMApiServiceTests {
    @Autowired
    private FCMApiService fcmApiService;

    @DisplayName("구독 요청 서비스 테스트")
    @Test
    void subscribeTest() {
        RequestFCMMessage.Subscribe message = RequestFCMMessage.Subscribe.builder()
                .token("feelz-2ZH-kiOMNHF9dLC0:APA91bEHgfbHDp5l0n3OMlFcdb2yazuPnaPuTXwUPUOkzc2KxAqZJU8mbh5D4Rfiy9tRim-WfsYKdZ6BT-UVxV9a4gtreWpygJiYG_b6gCrAMZ9HiXYckQdXKWpNXU9zyxsrpN9Xo_lF")
                .subscribeTypes(Arrays.asList(SubscribeType.CAREER, SubscribeType.SOFTWARE))
                .build();
        fcmApiService.pushToKafkaSubscribeMessage(message);
    }
}
