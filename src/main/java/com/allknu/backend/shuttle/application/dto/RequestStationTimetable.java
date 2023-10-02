package com.allknu.backend.shuttle.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class RequestStationTimetable {

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StationTime {
        @NotNull(message = "정류장이 비었다")
        private String station;

        @NotNull(message = "시간이 비었다")
        @JsonFormat(pattern = "HH:mm:ss", timezone = "Asia/Seoul")
        private Date time;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class registerStationTime {
        @NotNull(message = "정류장이 비었다")
        private String station;

        @NotNull(message = "시간이 비었다")
        @JsonFormat(pattern = "HH:mm:ss", timezone = "Asia/Seoul")
        private Date time;

        @NotNull(message = "도착지가 비었다")
        private String destination;
    }
}
