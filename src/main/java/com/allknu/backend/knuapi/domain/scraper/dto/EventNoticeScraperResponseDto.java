package com.allknu.backend.knuapi.domain.scraper.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class EventNoticeScraperResponseDto {

    private List<ScraperEventDetail> eventNoticeList;
    @Getter
    @Builder
    public static class ScraperEventDetail{
        private String title;
        private String link;
        private String date;
        private String writer;
        private String views;
    }
}
