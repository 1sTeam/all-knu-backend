package com.allknu.backend.web.dto;

import com.allknu.backend.core.types.SubscribeType;
import com.allknu.backend.kafka.dto.FCMSubscribeMessage;
import com.allknu.backend.kafka.dto.FCMWebMessage;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

//https://velog.io/@ausg/Spring-Boot%EC%97%90%EC%84%9C-%EA%B9%94%EB%81%94%ED%95%98%EA%B2%8C-DTO-%EA%B4%80%EB%A6%AC%ED%95%98%EA%B8%B0
public class RequestFCMMessage {

    @Builder
    @Data
    public static class Web {
        private List<SubscribeType> subscribeTypes; //보내고자하는 구독 유형 리스트
        @NotEmpty(message = "제목이 비어있다.")
        private String title;
        @NotEmpty(message = "내용이 비어있다.")
        private String body;
        @NotEmpty(message = "클릭 링크가 비어있다.")
        private String clickLink;

        public FCMWebMessage toFCMRequestMessage() {
            return FCMWebMessage.builder()
                    .subscribeTypes(this.subscribeTypes)
                    .title(this.title)
                    .body(this.body)
                    .clickLink(this.clickLink)
                    .build();
        }
    }

    @Builder
    @Data
    public static class Subscribe {
        @NotEmpty(message = "토큰이 비어있습니다")
        private String token; // 토큰
        @NotNull(message = "구독 유형이 없습니다.")
        private List<SubscribeType> subscribeTypes;

        public FCMSubscribeMessage toFCMSubscribeMessage() {
            return FCMSubscribeMessage.builder()
                    .token(this.token)
                    .subscribes(this.subscribeTypes)
                    .build();
        }
    }
}