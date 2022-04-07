package com.allknu.backend.web.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
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
    @Data
    @Builder
    public static class VeriusSatisfaction {
        private String number;
        private String name;
        private String operationEndDate;
        private String satisfactionEndDate;
        private String status;
        private String link;
    }
    @Data
    @Builder
    public static class MyVeriusProgram {
        private String number;                  //번호(fnDetail, 식별자)
        private String title;                   //제목
        private Date applicationDate;           //신청일
        private Date operationPeriodStart;      //운영기간 시작
        private Date operationPeriodEnd;        //운영기간 끝
        private String department;              //부서
        private String link;                    //제목 링크
    }
    @Data
    @Builder
    public static class Staff {
        private String workOn;
        private String userName;
        private String mail;
        private String office;
        private String department;
        private String location;
    }
}
