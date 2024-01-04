package com.allknu.backend.global.asset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@AllArgsConstructor
@ConfigurationProperties(prefix = "api-endpoint")
public class ApiEndpointSecretProperties {

    private final Crawling crawling;
    private final Mobile mobile;

    @Getter
    @AllArgsConstructor
    public static class Crawling {

        private final String userAgent;
        private final String ssoRetUrl;
        private final String ssoLogin;
        private final String veriusSsoLogin;
        private final String knuInfoSystemLogin;
        private final String univNotice;
        private final String univNoticeItem;
        private final String eventNotice;
        private final String eventNoticeItem;
        private final String knuCalendar;
        private final String veriusStaffInfo;
        private final String veriusMySatisfactionInfo;
        private final String veriusMySatisfactionSurvey;
        private final String veriusMyProgram;
        private final String veriusMyProgramLinkTypeOne;
        private final String veriusMyProgramLinkTypeTwo;
        private final String veriusMyMileage;
    }

    @Getter
    @AllArgsConstructor
    public static class Mobile {

        private final String login;
        private final String logout;
        private final String timetable;
        private final String periodUniv;
        private final String grade;
        private final String knuCalendar;
        private final String myScholarship;
        private final String myTuition;
        private final String knuStaffInfo;
    }
}
