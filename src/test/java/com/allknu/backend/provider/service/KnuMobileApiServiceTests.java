package com.allknu.backend.provider.service;

import com.allknu.backend.core.service.AuthService;
import com.allknu.backend.core.service.KnuMobileApiService;
import com.allknu.backend.web.dto.RequestKnu;
import com.allknu.backend.web.dto.ResponseKnu;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@ActiveProfiles("test") // 테스트서버 프로파일 적용
@TestPropertySource("classpath:/secrets/personal-account-secrets.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 테스트 인스턴스의 라이프 사이클을 클래스 단위로 설정
public class KnuMobileApiServiceTests {
    @Autowired
    private KnuMobileApiService knuMobileApiService;
    @Autowired
    private AuthService authService;
    @Value("${knu.id}")
    private String id;
    @Value("${knu.password}")
    private String password;
    private Map<String, String> loginCookies;

    @BeforeAll
    void before() {
        RequestKnu.Login login = RequestKnu.Login.builder()
                .id(id)
                .password(password)
                .build();
        loginCookies = authService.knuMobileLogin(login.getId(), login.getPassword()).orElseGet(()->null);
    }
    @AfterAll
    void after() {
        authService.knuMobileLogout(loginCookies); // 로그아웃
    }

    @Test
    @DisplayName("시간표 조회 테스트")
    void getTimeTableTest() {
        if(loginCookies != null) {
            for( Map.Entry<String, String> elem : loginCookies.entrySet() ){
                System.out.println( String.format("키 : %s, 값 : %s", elem.getKey(), elem.getValue()) );
            }
            ResponseKnu.TimeTable timeTable = knuMobileApiService.getTimeTable(loginCookies).orElseGet(()->null);
            assertNotNull(timeTable);
            System.out.println(timeTable.getData());
        }
    }

    @Test
    @DisplayName("재학 기간 조회 테스트")
    void getPeriodOfUnivTest() {
        if(loginCookies != null) {
            ResponseKnu.PeriodUniv period = knuMobileApiService.getPeriodOfUniv(loginCookies).orElseGet(()->null);
            assertNotNull(period);
            System.out.println(period.getData());
        }
    }
    @Test
    @DisplayName("성적 조회 테스트")
    void getGradeTest() {
        if(loginCookies != null) {
            ResponseKnu.Grade grade = knuMobileApiService.getGrade(loginCookies, "2021", "1").orElseGet(()->null);
            assertNotNull(grade);
            System.out.println(grade.getData());
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
        if(loginCookies != null) {
            List<ResponseKnu.ScholarshipItem> items = knuMobileApiService.getMyScholarship(loginCookies).orElseGet(()->null);
            assertNotNull(items);

            for (ResponseKnu.ScholarshipItem item : items){
                System.out.println(item.getAmount() + " " + item.getDescribe() + " " + item.getYear() + " " + item.getDepartment() + " " + item.getSemester() + " " + item.getGrade());
            }
        }
    }
    @Test
    @DisplayName("등록금 조회 테스트")
    void getMyTuitionTest() {
        if(loginCookies != null) {
            ResponseKnu.Tuition tuition = knuMobileApiService.getMyTuition(loginCookies, 2021, 1).orElseGet(()->null);
            assertNotNull(tuition);

            System.out.println(tuition.getAmount() + " " + tuition.getBank() + " " + tuition.getBankNumber() + " " + tuition.getDividedAmount() + " " + tuition.getDividedPay() + " "+ tuition.getDate()+" " + tuition.getTerm());
        }
    }
    @Test
    @DisplayName("교직원 조회 테스트")
    void getStaffList() {
        List<ResponseKnu.Staff> list = knuMobileApiService.getKnuStaffInfo().orElseGet(()->List.of());
        assertNotEquals(list.size(), 0);

        for (ResponseKnu.Staff staff : list) {
            System.out.println(staff.getDepartment() + " " + staff.getLocation() + " " + staff.getMail() + " " + staff.getOffice()+" " + staff.getUserName()+" " + staff.getWorkOn());
        }
    }
}
