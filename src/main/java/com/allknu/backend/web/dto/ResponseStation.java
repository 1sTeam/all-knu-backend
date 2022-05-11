package com.allknu.backend.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class ResponseStation {
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StationTime {
        private String station;
        private List<Date> stopTime;
    }
}
