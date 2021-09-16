package com.allknu.backend.kafka;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class KafkaProducerTests {
    @Autowired
    private MessageProducer messageProducer;

    @DisplayName("카프카 메시지 전송 테스트")
    @Test
    void messageSendTest() {
        //given
        String message = "hello world!";
        //when
        messageProducer.sendMessage(message);
        //then
    }
}
