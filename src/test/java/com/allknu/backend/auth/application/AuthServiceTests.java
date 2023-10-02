package com.allknu.backend.auth.application;

import com.allknu.backend.global.exception.errors.LoginFailedException;
import com.allknu.backend.knuapi.application.dto.RequestKnu;
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
public class AuthServiceTests {
    @Autowired
    private AuthService authService;

    @Value("${knu.id}")
    private String id;
    @Value("${knu.password}")
    private String password;

    @Test
    @DisplayName("sso login 테스트")
    void ssoLoginTest() {
        Map<String, String> cookies = authService.knuSsoLogin(id,password).orElseThrow(()->new LoginFailedException());
        for( Map.Entry<String, String> elem : cookies.entrySet() ){
            System.out.println( String.format("키 : %s, 값 : %s", elem.getKey(), elem.getValue()) );
        }
        assertNotNull(cookies);
    }

    @Test
    @DisplayName("mobile 로그인 테스트")
    void loginTest() {
        RequestKnu.Login login = RequestKnu.Login.builder()
                .id(id)
                .password(password)
                .build();

        Map<String, String> loginCookies = authService.knuMobileLogin(login.getId(), login.getPassword()).orElseGet(()->null);

        if(loginCookies != null) {
            for( Map.Entry<String, String> elem : loginCookies.entrySet() ){
                System.out.println( String.format("키 : %s, 값 : %s", elem.getKey(), elem.getValue()) );
            }
        }
    }
}
