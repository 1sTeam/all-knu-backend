package com.allknu.backend.provider.service;

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
public class KnuVeriusApiServiceTests {
    @Autowired
    private KnuVeriusApiService knuVeriusApiService;
    @Autowired
    private KnuApiService knuApiService;

    @Test
    @DisplayName("학생 정보 가져오기 테스트")
    void getStudentInfoTest() {
        // sso login
        Map<String, String> cookies = knuApiService.ssoLogin("1234","1234").orElseGet(()->null);
        assertNotNull(cookies);

        for( Map.Entry<String, String> elem : cookies.entrySet() ){
            System.out.println( String.format("키 : %s, 값 : %s", elem.getKey(), elem.getValue()) );
        }
        //get student info
        Map<String, String> info = knuVeriusApiService.getStudentInfo(cookies).orElseGet(()->null);
        assertNotNull(info);
        System.out.println("정보출력");
        for( Map.Entry<String, String> elem : info.entrySet() ){
            System.out.println( String.format("키 : %s, 값 : %s", elem.getKey(), elem.getValue()) );
        }
    }
    @Test
    @DisplayName("만족도 조사 참여 현황 테스트")
    void getSatisfactionTest() {
        // sso login
        Map<String, String> cookies = knuApiService.ssoLogin("1234","1234").orElseGet(()->null);
        assertNotNull(cookies);

        //get student info
        List<ResponseKnu.VeriusSatisfaction> list = knuVeriusApiService.getMyVeriusSatisfactionInfo(cookies, 1).orElseGet(()->null);
        assertNotNull(list);
        System.out.println("정보출력");
        for( ResponseKnu.VeriusSatisfaction elem : list ){
            System.out.println(elem.getName()+" "+elem.getNumber()+" "+elem.getSatisfactionEndDate()+" "+elem.getOperationEndDate()+" "+elem.getStatus());
        }
    }
}
