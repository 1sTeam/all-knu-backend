package com.allknu.backend.provider.service;

import com.allknu.backend.exception.errors.LoginFailedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("local")
public class KnuApiServiceTests {
    @Autowired
    private KnuApiService knuApiService;

    @Test
    @DisplayName("sso login 테스트")
    void ssoLoginTest() {
        Map<String, String> cookies = knuApiService.ssoLogin("201709999","1234").orElseThrow(()->new LoginFailedException());
        for( Map.Entry<String, String> elem : cookies.entrySet() ){
            System.out.println( String.format("키 : %s, 값 : %s", elem.getKey(), elem.getValue()) );
        }
        assertNotNull(cookies);
    }
}
