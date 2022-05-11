package com.allknu.backend.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class RequestStationTimetable {
    @ToString
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class addStationTime {
        @NotNull(message = "정류장이 비었다")
        private String station;

        @NotNull(message = "시간이 비었다")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "Asia/Seoul")
        private Date time;
    }
}
