package com.allknu.backend.knuapi.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

public class ResponseCrawling {
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UnivNotice {
        private String title;
        private String link;
        private String type;
        private String writer;
        private String date;
        private String views;
        private String number;
    }
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EventNotice {
        private String title;
        private String link;
        private String writer;
        private String date;
        private String views;
    }
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Schedule {
        private String date;
        private String content;
    }
}
