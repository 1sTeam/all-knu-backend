package com.allknu.backend.web.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

public class RequestKnu {

    @Builder
    @Data
    public static class Login {
        @NotNull(message = "아이디가 비었다")
        private String id;
        @NotNull(message = "비밀번호가 비었다")
        private String password;
    }
}
