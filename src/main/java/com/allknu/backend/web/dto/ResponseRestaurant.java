package com.allknu.backend.web.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

public class ResponseRestaurant {
    @Builder
    @Data
    public static class FindMenu {
        private String name;
        private List<String> breakfast;
        private List<String> lunch;
        private List<String> dinner;
    }
}
