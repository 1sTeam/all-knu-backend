package com.allknu.backend.web.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

public class ResponseKnu {

    @Data
    @Builder
    public static class TimeTable {
        private List<Object> data;
    }
}
