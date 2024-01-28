package com.allknu.backend.knuapi.domain.scraper.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class EventNoticeResponseDto {

    private Map<String, List<EventDetail>> eventNoticeMap;


    @Getter
    @Builder
    public static class EventDetail{
        private String link;
        private String date;
        private String writer;
        private String views;
        private String title;
    }
}
