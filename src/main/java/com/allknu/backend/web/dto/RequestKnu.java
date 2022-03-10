package com.allknu.backend.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Map;

public class RequestKnu {

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Login {
        @NotNull(message = "아이디가 비었다")
        private String id;
        @NotNull(message = "비밀번호가 비었다")
        private String password;
        @Null
        private SessionInfo sessionInfo;
    }

    @Builder
    @Data
    public static class Grade {
        @NotNull(message = "강남대 쿠키 넣어주세요")
        private Map<String, String> cookies;
        private String year; // 년도
        private String semester; // 학기
    }
    @Builder
    @Data
    public static class Tuition {
        @NotNull(message = "강남대 쿠키 넣어주세요")
        private Map<String, String> cookies;
        private Integer year; // 년도
        private Integer semester; // 학기

    }
}
