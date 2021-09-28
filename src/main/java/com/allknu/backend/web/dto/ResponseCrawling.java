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
    }
}