package com.allknu.backend.knuapi.application.dto;

import com.allknu.backend.knuapi.domain.scraper.dto.KnuVeriusSatisfactionSurveyScraperResponseDto;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class KnuVeriusSatisfactionSurveyResponseDto {

    private List<VeriusSatisfactionItemDto> items;

    @Getter
    @Builder
    public static class VeriusSatisfactionItemDto {

        private String number;
        private String name;
        private String operationEndDate;
        private String satisfactionEndDate;
        private String status;
        private String link;
    }

    public static KnuVeriusSatisfactionSurveyResponseDto from(KnuVeriusSatisfactionSurveyScraperResponseDto response) {
        List<VeriusSatisfactionItemDto> items = response.getItems().stream()
                .map(item -> VeriusSatisfactionItemDto.builder()
                        .number(item.getNumber())
                        .name(item.getName())
                        .operationEndDate(item.getOperationEndDate())
                        .satisfactionEndDate(item.getSatisfactionEndDate())
                        .status(item.getStatus())
                        .link(item.getLink())
                        .build())
                .collect(Collectors.toList());

        return new KnuVeriusSatisfactionSurveyResponseDto(items);
    }
}
