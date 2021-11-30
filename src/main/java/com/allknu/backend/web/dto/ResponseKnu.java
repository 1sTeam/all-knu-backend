package com.allknu.backend.web.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

public class ResponseKnu {

    @Data
    @Builder
    public static class TimeTable {
        private Object data;
    }
    @Data
    @Builder
    public static class PeriodUniv {
        private Object data;
    }
    @Data
    @Builder
    public static class Grade {
        private Object data;
    }
    @Data
    @Builder
    public static class CalendarItem {
        private String year;
        private String start;
        private String end;
        private String describe;
    }
}
