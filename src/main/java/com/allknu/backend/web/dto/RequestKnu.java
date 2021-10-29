package com.allknu.backend.web.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;

public class RequestKnu {

    @Builder
    @Data
    public static class Login {
        @NotNull(message = "아이디가 비었다")
        private String id;
        @NotNull(message = "비밀번호가 비었다")
        private String password;
    }

    @Builder
    @Data
    public static class Grade {
        @NotNull(message = "강남대 쿠키 넣어주세요")
        private Map<String, String> cookies;
        private String year; // 년도
        private String semester; // 학기
    }
}
