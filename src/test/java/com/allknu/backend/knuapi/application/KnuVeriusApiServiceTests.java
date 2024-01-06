package com.allknu.backend.knuapi.application;

import com.allknu.backend.auth.application.AuthServiceImpl;
import com.allknu.backend.knuapi.application.KnuVeriusApiService;
import com.allknu.backend.knuapi.application.dto.KnuVeriusSatisfactionSurveyResponseDto;
import com.allknu.backend.knuapi.application.dto.ResponseKnu;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test") // 테스트서버 프로파일 적용
@TestPropertySource("classpath:/secrets/personal-account-secrets.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 테스트 인스턴스의 라이프 사이클을 클래스 단위로 설정
public class KnuVeriusApiServiceTests {
    @Autowired
    private KnuVeriusApiService knuVeriusApiService;
    @Autowired
    private AuthServiceImpl authService;
    @Value("${knu.id}")
    private String id;
    @Value("${knu.password}")
    private String password;
    private Map<String, String> veriusCookies;

    @BeforeAll
    void beforeAll() {
        //testInstance덕에 static 아니여도 됨
        Map<String, String> ssoCookies = authService.knuSsoLogin(id,password).orElseGet(()->null);
        assertNotNull(ssoCookies);
        veriusCookies = authService.veriusLogin(ssoCookies).orElseGet(()->null);
        assertNotNull(veriusCookies);
    }
    @AfterAll
    void afterAll() {

    }

    @Test
    @DisplayName("학생 정보 가져오기 테스트")
    void getStudentInfoTest() {
        // sso login

        //get student info
        Map<String, String> info = knuVeriusApiService.getStudentInfo(veriusCookies).orElseGet(()->null);
        assertNotNull(info);
        System.out.println("정보출력");
        for( Map.Entry<String, String> elem : info.entrySet() ){
            System.out.println( String.format("키 : %s, 값 : %s", elem.getKey(), elem.getValue()) );
        }
    }
    @Test
    @DisplayName("만족도 조사 참여 현황 테스트")
    void getSatisfactionTest() {
        //get student info
        KnuVeriusSatisfactionSurveyResponseDto responseDto = knuVeriusApiService.getMyVeriusSatisfactionInfo(veriusCookies, 1);

        System.out.println("정보출력");
        for (KnuVeriusSatisfactionSurveyResponseDto.VeriusSatisfactionItemDto elem : responseDto.getItems()){
            System.out.println(elem.getName()+" "+elem.getNumber()+" "+elem.getSatisfactionEndDate()+" "+elem.getOperationEndDate()+" "+elem.getStatus() + " " + elem.getLink());
        }
    }
    @Test
    @DisplayName("참여 비교과 조회 테스트")
    void getMyVeriusProgramTest() {
        List<ResponseKnu.MyVeriusProgram> list = knuVeriusApiService.getMyVeriusProgram(veriusCookies, 10).orElseGet(()->null);
        assertNotNull(list);
        System.out.println("정보출력--------------------");
        if(list.size() == 0){       //값이 없을 시 빈배열 반환 확인
            System.out.println(list);
        }
        for( ResponseKnu.MyVeriusProgram elem : list ){
            System.out.println(elem.getDepartment()+" "+elem.getTitle()+" "+elem.getNumber()+" "+elem.getApplicationDate()+" "+elem.getOperationPeriodStart()+" "+elem.getOperationPeriodEnd() + " " + elem.getLink());
        }
    }
    @Test
    @DisplayName("마일리지 조회 테스트")
    void getMileage(){
        Map<String,Map<String,Integer>> list = knuVeriusApiService.getMileage(veriusCookies).orElseGet(()->null);
        System.out.println(list);

    }
}
