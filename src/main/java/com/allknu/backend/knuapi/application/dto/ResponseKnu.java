package com.allknu.backend.knuapi.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

public class ResponseKnu {

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TimeTable {
        private Object data;
    }
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PeriodUniv {
        private Object data;
    }
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Grade {
        private Object data;
    }
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CalendarItem {
        private String year;
        private String start;
        private String end;
        private String describe;
    }
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScholarshipItem {
        private String amount;
        private String department;
        private String year;
        private String grade;
        private String semester;
        private String describe;
    }
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Tuition {
        private String date;
        private String bank;
        private String bankNumber;
        private String term;
        private String amount;
        private String dividedAmount;
        private String dividedPay;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyVeriusProgram {
        private String number;                  //번호(fnDetail, 식별자)
        private String title;                   //제목
        private Date applicationDate;           //신청일
        private Date operationPeriodStart;      //운영기간 시작
        private Date operationPeriodEnd;        //운영기간 끝
        private String department;              //부서
        private String link;                    //제목 링크
    }
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Staff {
        private String workOn;
        private String userName;
        private String mail;
        private String office;
        private String department;
        private String location;
    }
}
