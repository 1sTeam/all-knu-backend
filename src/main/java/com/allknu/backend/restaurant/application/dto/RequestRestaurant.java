package com.allknu.backend.restaurant.application.dto;

import com.allknu.backend.restaurant.domain.MealType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;

public class RequestRestaurant {
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterRestaurant {
        @NotNull(message = "식당이 입력되지 않음")
        private String restaurant;
    }
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterMenu {
        @NotNull(message = "식당이 입력되지 않음")
        private String restaurant;
        @NotNull(message = "메뉴별 날짜가 입력되지 않음")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date date;
        @NotNull(message = "메뉴가 입력되지 않음")
        private List<String> menu;
        @NotNull(message = "메뉴 타입이 입력되지 않음")
        private MealType time;
    }
}
