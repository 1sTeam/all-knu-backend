package com.allknu.backend.provider.service;

import com.allknu.backend.web.dto.RequestKnu;
import com.allknu.backend.web.dto.ResponseKnu;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("local")
public class KnuApiServiceTests {
    @Autowired
    private KnuApiService knuApiService;

    @Test
    @DisplayName("로그인 테스트")
    void loginTest() {
        RequestKnu.Login login = RequestKnu.Login.builder()
                .id("1234")
                .password("1234")
                .build();
        Map<String, String> cookies = knuApiService.login(login.getId(), login.getPassword()).orElseGet(()->null);
        if(cookies != null) {
            for( Map.Entry<String, String> elem : cookies.entrySet() ){
                System.out.println( String.format("키 : %s, 값 : %s", elem.getKey(), elem.getValue()) );
            }
            knuApiService.logout(cookies); // 로그아웃
        }
        //assertNotNull(cookies);
    }

    @Test
    @DisplayName("시간표 조회 테스트")
    void getTimeTableTest() {
        //로그인
        RequestKnu.Login login = RequestKnu.Login.builder()
                .id("201704017")
                .password("1234")
                .build();
        Map<String, String> cookies = knuApiService.login(login.getId(), login.getPassword()).orElseGet(()->null);
        if(cookies != null) {
            for( Map.Entry<String, String> elem : cookies.entrySet() ){
                System.out.println( String.format("키 : %s, 값 : %s", elem.getKey(), elem.getValue()) );
            }
            ResponseKnu.TimeTable timeTable = knuApiService.getTimeTable(cookies).orElseGet(()->null);
            //assertNotNull(timeTable);
            //System.out.println(timeTable.getData());

            knuApiService.logout(cookies); // 로그아웃
        }
    }

    @Test
    @DisplayName("재학 기간 조회 테스트")
    void getPeriodOfUnivTest() {
        //로그인
        RequestKnu.Login login = RequestKnu.Login.builder()
                .id("201704017")
                .password("1234")
                .build();
        Map<String, String> cookies = knuApiService.login(login.getId(), login.getPassword()).orElseGet(()->null);
        if(cookies != null) {
            ResponseKnu.PeriodUniv period = knuApiService.getPeriodOfUniv(cookies).orElseGet(()->null);
            //assertNotNull(period);
            //System.out.println(period.getData());

            knuApiService.logout(cookies); // 로그아웃
        }
    }
    @Test
    @DisplayName("성적 조회 테스트")
    void getGradeTest() {
        //로그인
        RequestKnu.Login login = RequestKnu.Login.builder()
                .id("201704017")
                .password("1234")
                .build();
        Map<String, String> cookies = knuApiService.login(login.getId(), login.getPassword()).orElseGet(()->null);
        if(cookies != null) {
            ResponseKnu.Grade grade = knuApiService.getGrade(cookies, "2021", "1").orElseGet(()->null);
            //assertNotNull(grade);
            //System.out.println(grade.getData());

            knuApiService.logout(cookies); // 로그아웃
        }
    }
}
