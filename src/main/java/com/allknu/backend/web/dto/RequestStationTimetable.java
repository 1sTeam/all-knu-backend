package com.allknu.backend.web.dto;

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
}
