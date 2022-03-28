package com.allknu.backend.web.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

public class ResponseCrawling {
    @Builder
    @Data
    public static class UnivNotice {
        private String title;
        private String link;
        private String type;
        private String writer;
        private String date;
        private String views;
        private String number;
    }
    @Data
    @Builder
    public static class Calendar {
        private String date;
        private String content;
    }
}
