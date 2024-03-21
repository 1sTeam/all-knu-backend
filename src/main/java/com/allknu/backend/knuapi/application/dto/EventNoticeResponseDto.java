package com.allknu.backend.knuapi.application.dto;

import com.allknu.backend.knuapi.domain.scraper.dto.EventNoticeScraperResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class EventNoticeResponseDto {

    private List<EventDetail> eventNoticeList;

    @Getter
    @Builder
    public static class EventDetail{
        private String link;
        private String date;
        private String writer;
        private String views;
        private String title;
    }

    // EventNoticeScraperResponseDto -> EventNoticeResponseDto 변환
    public static EventNoticeResponseDto from(EventNoticeScraperResponseDto eventNoticeScraperResponseDto) {
        List<EventDetail> eventNoticeList = eventNoticeScraperResponseDto.getEventNoticeList().stream()
                .map(item -> EventDetail.builder()
                        .title(item.getTitle())
                        .link(item.getLink())
                        .date(item.getDate())
                        .writer(item.getWriter())
                        .views(item.getViews())
                        .build())
                .collect(Collectors.toList());

        return new EventNoticeResponseDto(eventNoticeList);
    }
}
