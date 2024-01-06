package com.allknu.backend.knuapi.application.dto;

import com.allknu.backend.knuapi.domain.scraper.dto.KnuCalenderScraperResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class CalendarResponseDto {

    private Map<String, List<CalendarItemDto>> calendarMap;

    public static CalendarResponseDto empty() {
        return new CalendarResponseDto(new HashMap<>());
    }

    public static CalendarResponseDto from(KnuCalenderScraperResponseDto knuCalenderScraperResponseDto) {
        Map<String, List<CalendarItemDto>> calendarMap = new HashMap<>();

        for (var entry : knuCalenderScraperResponseDto.getCalendarMap().entrySet()) {
            List<CalendarItemDto> list = entry.getValue().stream()
                    .map(item -> CalendarItemDto.builder()
                            .date(item.getDate())
                            .content(item.getContent())
                            .build())
                    .collect(Collectors.toList());

            calendarMap.put(entry.getKey(), list);
        }

        return new CalendarResponseDto(calendarMap);
    }

    @Getter
    @Builder
    public static class CalendarItemDto {

        private String date;
        private String content;
    }
}
