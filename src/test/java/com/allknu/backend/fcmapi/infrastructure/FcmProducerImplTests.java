package com.allknu.backend.fcmapi.infrastructure;

import com.allknu.backend.fcmapi.domain.SubscribeType;
import com.allknu.backend.fcmapi.infrastructure.FcmProducerImpl;
import com.allknu.backend.fcmapi.domain.dto.FCMMobileMessage;
import com.allknu.backend.fcmapi.domain.dto.FCMSubscribeMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class FcmProducerImplTests {
    @Autowired
    private FcmProducerImpl fcmProducerImpl;

    @DisplayName("카프카 메시지 전송 테스트")
    @Test
    void requestFCMMessageTest() {
        //given
        List<SubscribeType> subs = Arrays.asList(SubscribeType.CAREER, SubscribeType.SOFTWARE);
        FCMMobileMessage message = FCMMobileMessage.builder()
                                        .subscribeTypes(subs)
                                        .title("world")
                                        .body("hello")
                                        .build();
        //when
        fcmProducerImpl.sendFCMMobileMessage(message);
    }

    @DisplayName("카프카 구독 메시지 전송 테스트")
    @Test
    void requestSubscribeTest() {
        List<SubscribeType> subs = Arrays.asList(SubscribeType.CAREER, SubscribeType.SOFTWARE);
        FCMSubscribeMessage message = FCMSubscribeMessage
                .builder()
                .subscribes(subs)
                .token("abcd")
                .build();
        fcmProducerImpl.sendFCMRequestSubscribeMessage(message);
    }
}
