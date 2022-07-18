package com.allknu.backend.provider.service;

import com.allknu.backend.core.service.KnuApiService;
import com.allknu.backend.core.service.KnuInfoSystemService;
import com.allknu.backend.exception.errors.LoginFailedException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource("classpath:/secrets/personal-account-secrets.properties")
public class KnuInfoSystemServiceTests {
    @Autowired
    KnuInfoSystemService knuInfoSystemService;
    @Autowired
    KnuApiService knuApiService;
    @Value("${knu.id}")
    private String id;
    @Value("${knu.password}")
    private String password;

    @Test
    void infoSystemLoginTest() {
        Map<String, String> ssoInfo = knuApiService.ssoLogin(id, password).orElseThrow(()->new LoginFailedException());
        Map<String, String> knuInfo = knuInfoSystemService.knuInfoSystemLogin(ssoInfo).orElseThrow(()->new LoginFailedException());
        assertNotNull(knuInfo);
    }
}
