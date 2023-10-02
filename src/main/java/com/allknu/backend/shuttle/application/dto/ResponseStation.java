package com.allknu.backend.shuttle.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getStationTime {
        private String station;
        private List<ResponseStationTimetable> stopTime;
    }
}
