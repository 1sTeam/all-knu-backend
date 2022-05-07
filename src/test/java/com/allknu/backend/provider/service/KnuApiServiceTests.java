package com.allknu.backend.provider.service;

import com.allknu.backend.core.service.KnuApiService;
import com.allknu.backend.exception.errors.LoginFailedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test") // 테스트서버 프로파일 적용
@TestPropertySource("classpath:/secrets/personal-account-secrets.properties")
public class KnuApiServiceTests {
    @Autowired
    private KnuApiService knuApiService;

    @Value("${knu.id}")
    private String id;
    @Value("${knu.password}")
    private String password;

    @Test
    @DisplayName("sso login 테스트")
    void ssoLoginTest() {
        Map<String, String> cookies = knuApiService.ssoLogin(id,password).orElseThrow(()->new LoginFailedException());
        for( Map.Entry<String, String> elem : cookies.entrySet() ){
            System.out.println( String.format("키 : %s, 값 : %s", elem.getKey(), elem.getValue()) );
        }
        assertNotNull(cookies);
    }
}
