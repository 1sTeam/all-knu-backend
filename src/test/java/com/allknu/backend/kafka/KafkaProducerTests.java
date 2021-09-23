package com.allknu.backend.kafka;

import com.allknu.backend.kafka.dto.FCMRequestMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("local")
public class KafkaProducerTests {
    @Autowired
    private MessageProducer messageProducer;

    @DisplayName("카프카 메시지 전송 테스트")
    @Test
    void requestFCMMessageTest() {
        //given
        FCMRequestMessage message = FCMRequestMessage.builder()
                                        .sendType("all")
                                        .target("abcd")
                                        .title("world")
                                        .body("hello")
                                        .build();
        //when
        messageProducer.sendFCMMessage(message);
    }
}
