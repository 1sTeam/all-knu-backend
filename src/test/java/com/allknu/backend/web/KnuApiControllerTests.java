package com.allknu.backend.web;

import com.allknu.backend.provider.service.KnuMobileApiService;
import com.allknu.backend.web.dto.RequestKnu;
import com.allknu.backend.web.dto.ResponseKnu;
import com.allknu.backend.web.dto.SessionInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource("classpath:/secrets/personal-account-secrets.properties")
public class KnuApiControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Value("${knu.id}")
    private String id;
    @Value("${knu.password}")
    private String password;

    @DisplayName("모바일 세션 유효 시 재발급 방지 테스트")
    @Test
    void login() throws Exception{
        // 기존 서비스로 모바일 세션을 받아온다.
        KnuMobileApiService knuMobileApiService = new KnuMobileApiService();
        Map<String, String> mobileCookies = knuMobileApiService.login(id,password,null).orElseGet(()->null);
        System.out.println(mobileCookies == null);

        SessionInfo sessionInfo = SessionInfo.builder()
                .mobileCookies(mobileCookies).build();
        // 모바일 세션을 넣어 목을 호출해 세션 재발급 일어나지 않는 것을 확인해본다
        RequestKnu.Login loginDto =RequestKnu.Login.builder()
                .id(id)
                .password(password)
                .sessionInfo(sessionInfo)
                .build();

        Map<String, Object> input = new HashMap<>();
        input.put("id", loginDto.getId());
        input.put("password", loginDto.getPassword());
        input.put("sessionInfo", loginDto.getSessionInfo());
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(input));

        mockMvc.perform(
                post("/knu/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andDo(print());

    }
}

