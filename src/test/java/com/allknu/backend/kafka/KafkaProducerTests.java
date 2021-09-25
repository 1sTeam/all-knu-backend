package com.allknu.backend.kafka;

import com.allknu.backend.core.types.SubscribeType;
import com.allknu.backend.kafka.dto.FCMWebMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@ActiveProfiles("local")
public class KafkaProducerTests {
    @Autowired
    private MessageProducer messageProducer;

    @DisplayName("카프카 메시지 전송 테스트")
    @Test
    void requestFCMMessageTest() {
        //given
        List<SubscribeType> subs = Arrays.asList(SubscribeType.CAREER, SubscribeType.SOFTWARE);
        List<String> tokens = Arrays.asList("123","456");
        FCMWebMessage message = FCMWebMessage.builder()
                                        .subscribeTypes(subs)
                                        .tokens(tokens)
                                        .title("world")
                                        .body("hello")
                                        .build();
        //when
        messageProducer.sendFCMMessage(message);
    }
}
