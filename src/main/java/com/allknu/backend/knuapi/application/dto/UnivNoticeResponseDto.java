package com.allknu.backend.knuapi.application.dto;

import com.allknu.backend.knuapi.domain.scraper.dto.UnivNoticeScraperResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class UnivNoticeResponseDto {

    private List<UnivNoticeDto> univNoticeList;

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

    // UnivNoticeScraperResponseDto -> UnivNoticeResponseDto 변환
    public static UnivNoticeResponseDto from(UnivNoticeScraperResponseDto univNoticeScraperResponseDto) {
        List<UnivNoticeDto> univNoticeList = univNoticeScraperResponseDto.getUnivNoticeList().stream()
                .map(item -> UnivNoticeDto.builder()
                        .link(item.getLink())
                        .date(item.getDate())
                        .number(item.getNumber())
                        .writer(item.getWriter())
                        .type(item.getType())
                        .views(item.getViews())
                        .title(item.getTitle())
                        .build())
                .collect(Collectors.toList());

        return new UnivNoticeResponseDto(univNoticeList);
    }
}
