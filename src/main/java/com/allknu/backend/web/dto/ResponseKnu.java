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
    @Data
    @Builder
    public static class ScholarshipItem {
        private String amount;
        private String department;
        private String year;
        private String grade;
        private String semester;
        private String describe;
    }
    @Data
    @Builder
    public static class Tuition {
        private String date;
        private String bank;
        private String bankNumber;
        private String term;
        private String amount;
        private String dividedAmount;
        private String dividedPay;
    }
}
