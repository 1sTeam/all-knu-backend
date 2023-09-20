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
    }
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Refresh {
        @NotNull(message = "아이디가 비었다")
        private String id;
        @NotNull(message = "비밀번호가 비었다")
        private String password;
        private SessionInfo sessionInfo;
    }
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Logout {
        @NotNull(message = "강남대 쿠키 넣어주세요")
        private SessionInfo sessionInfo;
    }
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Timetable {
        @NotNull(message = "강남대 쿠키 넣어주세요")
        private SessionInfo sessionInfo;
    }
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyVeriusProgram {
        @NotNull(message = "강남대 쿠키 넣어주세요")
        private SessionInfo sessionInfo;
        private int page;
    }
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Period {
        @NotNull(message = "강남대 쿠키 넣어주세요")
        private SessionInfo sessionInfo;
    }
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Grade {
        @NotNull(message = "강남대 쿠키 넣어주세요")
        private SessionInfo sessionInfo;
        private String year; // 년도
        private String semester; // 학기
    }
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Scholarship {
        @NotNull(message = "강남대 쿠키 넣어주세요")
        private SessionInfo sessionInfo;
    }
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Tuition {
        @NotNull(message = "강남대 쿠키 넣어주세요")
        private SessionInfo sessionInfo;
        private Integer year; // 년도
        private Integer semester; // 학기
    }
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VeriusSatisfaction {
        @NotNull(message = "강남대 쿠키 넣어주세요")
        private SessionInfo sessionInfo;
        @NotNull(message = "페이지를 넣어주세요")
        private Integer page;
    }
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Mileage {
        @NotNull(message = "강남대 쿠키 넣어주세요")
        private SessionInfo sessionInfo;
    }
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VeriusLogin{
        @NotNull(message = "강남대 쿠키 넣어주세요")
        private SessionInfo sessionInfo;
    }
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MobileLogin{
        @NotNull(message = "아이디가 비었다")
        private String id;
        @NotNull(message = "비밀번호가 비었다")
        private String password;
    }
}
