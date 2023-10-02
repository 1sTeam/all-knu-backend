package com.allknu.backend.knuapi.presentation;

import com.allknu.backend.knuapi.application.dto.RequestKnu;
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

    @DisplayName("로그인 테스트")
    @Test
    void login() throws Exception{
        RequestKnu.Login loginDto =RequestKnu.Login.builder()
                .id(id)
                .password(password)
                .build();

        Map<String, Object> input = new HashMap<>();
        input.put("id", loginDto.getId());
        input.put("password", loginDto.getPassword());
        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(
                post("/knu/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andDo(print());

    }
}

