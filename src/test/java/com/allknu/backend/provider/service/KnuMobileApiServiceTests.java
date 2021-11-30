package com.allknu.backend.provider.service;

import com.allknu.backend.web.dto.RequestKnu;
import com.allknu.backend.web.dto.ResponseKnu;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@ActiveProfiles("local")
public class KnuMobileApiServiceTests {
    @Autowired
    private KnuMobileApiService knuMobileApiService;

    @Test
    @DisplayName("로그인 테스트")
    void loginTest() {
        RequestKnu.Login login = RequestKnu.Login.builder()
                .id("1234")
                .password("1234")
                .build();
        Map<String, String> cookies = knuMobileApiService.login(login.getId(), login.getPassword()).orElseGet(()->null);
        if(cookies != null) {
            for( Map.Entry<String, String> elem : cookies.entrySet() ){
                System.out.println( String.format("키 : %s, 값 : %s", elem.getKey(), elem.getValue()) );
            }
            knuMobileApiService.logout(cookies); // 로그아웃
        }
        //assertNotNull(cookies);
    }

    @Test
    @DisplayName("시간표 조회 테스트")
    void getTimeTableTest() {
        //로그인
        RequestKnu.Login login = RequestKnu.Login.builder()
                .id("1234")
                .password("1234")
                .build();
        Map<String, String> cookies = knuMobileApiService.login(login.getId(), login.getPassword()).orElseGet(()->null);
        if(cookies != null) {
            for( Map.Entry<String, String> elem : cookies.entrySet() ){
                System.out.println( String.format("키 : %s, 값 : %s", elem.getKey(), elem.getValue()) );
            }
            ResponseKnu.TimeTable timeTable = knuMobileApiService.getTimeTable(cookies).orElseGet(()->null);
            assertNotNull(timeTable);
            System.out.println(timeTable.getData());

            knuMobileApiService.logout(cookies); // 로그아웃
        }
    }

    @Test
    @DisplayName("재학 기간 조회 테스트")
    void getPeriodOfUnivTest() {
        //로그인
        RequestKnu.Login login = RequestKnu.Login.builder()
                .id("1234")
                .password("1234")
                .build();
        Map<String, String> cookies = knuMobileApiService.login(login.getId(), login.getPassword()).orElseGet(()->null);
        if(cookies != null) {
            ResponseKnu.PeriodUniv period = knuMobileApiService.getPeriodOfUniv(cookies).orElseGet(()->null);
            assertNotNull(period);
            System.out.println(period.getData());

            knuMobileApiService.logout(cookies); // 로그아웃
        }
    }
    @Test
    @DisplayName("성적 조회 테스트")
    void getGradeTest() {
        //로그인
        RequestKnu.Login login = RequestKnu.Login.builder()
                .id("1234")
                .password("1234")
                .build();
        Map<String, String> cookies = knuMobileApiService.login(login.getId(), login.getPassword()).orElseGet(()->null);
        if(cookies != null) {
            ResponseKnu.Grade grade = knuMobileApiService.getGrade(cookies, "2021", "1").orElseGet(()->null);
            assertNotNull(grade);
            System.out.println(grade.getData());

            knuMobileApiService.logout(cookies); // 로그아웃
        }
    }
    @Test
    @DisplayName("학사조회 테스트")
    void getCalendarTest() {
        List<ResponseKnu.CalendarItem> list = knuMobileApiService.getKnuCalendar().orElseGet(()->null);
        assertNotNull(list);
        for (ResponseKnu.CalendarItem item : list) {
            System.out.println(item.getYear() + " " + item.getStart() + " " + item.getEnd() + " " + item.getDescribe());
        }
    }
    @Test
    @DisplayName("장학 조회 테스트")
    void getMyScholarshipTest() {
        //로그인
        RequestKnu.Login login = RequestKnu.Login.builder()
                .id("1234")
                .password("1234")
                .build();
        Map<String, String> cookies = knuMobileApiService.login(login.getId(), login.getPassword()).orElseGet(()->null);
        if(cookies != null) {
            List<ResponseKnu.ScholarshipItem> items = knuMobileApiService.getMyScholarship(cookies).orElseGet(()->null);
            assertNotNull(items);

            for (ResponseKnu.ScholarshipItem item : items){
                System.out.println(item.getAmount() + " " + item.getDescribe() + " " + item.getYear() + " " + item.getDepartment() + " " + item.getSemester() + " " + item.getGrade());
            }

            knuMobileApiService.logout(cookies); // 로그아웃
        }
    }
    @Test
    @DisplayName("등록금 조회 테스트")
    void getMyTuitionTest() {
        //로그인
        RequestKnu.Login login = RequestKnu.Login.builder()
                .id("1234")
                .password("1234")
                .build();
        Map<String, String> cookies = knuMobileApiService.login(login.getId(), login.getPassword()).orElseGet(()->null);
        if(cookies != null) {
            ResponseKnu.Tuition tuition = knuMobileApiService.getMyTuition(cookies, 2021, 1).orElseGet(()->null);
            assertNotNull(tuition);

            System.out.println(tuition.getAmount() + " " + tuition.getBank() + " " + tuition.getBankNumber() + " " + tuition.getDividedAmount() + " " + tuition.getDividedPay() + " "+ tuition.getDate()+" " + tuition.getTerm());

            knuMobileApiService.logout(cookies); // 로그아웃
        }
    }
}
