package com.allknu.backend.knuapi.domain.scraper.dto;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UnivNoticeScraperResponseDto {

    private List<ScraperUnivNoticeDto> univNoticeList;

    @Getter
    @Builder
    public static class ScraperUnivNoticeDto {
        private String link;
        private String date;
        private String number;
        private String writer;
        private String type;
        private String views;
        private String title;
    }
}
