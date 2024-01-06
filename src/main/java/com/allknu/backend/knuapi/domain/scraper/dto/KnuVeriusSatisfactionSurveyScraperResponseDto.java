package com.allknu.backend.knuapi.domain.scraper.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class KnuVeriusSatisfactionSurveyScraperResponseDto {

    private List<KnuVeriusSatisfactionSurveyItem> items;

    @Builder
    @Getter
    public static class KnuVeriusSatisfactionSurveyItem {

        private String number;
        private String name;
        private String operationEndDate;
        private String satisfactionEndDate;
        private String status;
        private String link;
    }
}
