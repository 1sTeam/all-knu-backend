package com.allknu.backend.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class ResponseRestaurant {
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FindMenu {
        private String name;
        private List<String> breakfast;
        private List<String> lunch;
        private List<String> dinner;
    }
}
