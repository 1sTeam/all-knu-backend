package com.allknu.backend.fcmapi.presentation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FCMApiControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("구독 유형 조회 테스트")
    void getAllSubscribeTypeTest() throws Exception {
        mockMvc.perform(get("/knu/subscribe/{team}", "major"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
