package com.allknu.backend.fcmapi.application.dto;

import com.allknu.backend.fcmapi.domain.FirebaseLog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

public class ResponseFcm {

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Log {
        private Long id;
        private String adminEmail;
        private String title;
        private String body;
        private String link;
        private Date timestamp;

        public static Log of(FirebaseLog log) {
            //entity to dto
            return Log.builder()
                    .id(log.getId())
                    .adminEmail(log.getAdminEmail())
                    .title(log.getTitle())
                    .body(log.getBody())
                    .link(log.getLink())
                    .timestamp(log.getTimestamp())
                    .build();
        }
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SubscribeType {
        private String topic;
        private String korean;
        private String team;
    }
}
