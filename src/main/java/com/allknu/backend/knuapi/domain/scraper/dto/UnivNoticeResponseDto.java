package com.allknu.backend.knuapi.domain.scraper.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class UnivNoticeResponseDto {

    private Map<String, List<UnivNoticeDto>> univNoticeMap;

    @Getter
    @Builder
    public static class UnivNoticeDto {
        private String title;
        private String link;
        private String type;
        private String writer;
        private String date;
        private String views;
        private String number;
    }
}
