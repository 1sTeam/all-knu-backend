package com.allknu.backend.web.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class ResponseStationTimetable {
    private Date time;
    private String destination;

}
