package com.allknu.backend.knuapi.domain.scraper.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class KnuCalenderScraperResponseDto {

    private Map<String, List<CalendarItem>> calendarMap;

    @Getter
    @Builder
    public static class CalendarItem {

        private String date;
        private String content;
    }
}
