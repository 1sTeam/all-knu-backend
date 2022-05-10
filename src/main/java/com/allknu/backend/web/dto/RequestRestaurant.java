package com.allknu.backend.web.dto;

import com.allknu.backend.core.types.MealType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class RequestRestaurant {
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Restaurant {
        @NotNull(message = "식당이 입력되지 않음")
        private String restaurant;
    }
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Menu {
        @NotNull(message = "식당이 입력되지 않음")
        private String restaurant;
        @NotNull(message = "메뉴별 날짜가 입력되지 않음")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private Date date;
        @NotNull(message = "메뉴가 입력되지 않음")
        private List<String> menu;
        @NotNull(message = "메뉴 타입이 입력되지 않음")
        private MealType time;
    }

}
