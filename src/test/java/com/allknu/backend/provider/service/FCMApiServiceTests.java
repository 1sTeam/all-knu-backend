package com.allknu.backend.provider.service;

import com.allknu.backend.core.service.FCMApiService;
import com.allknu.backend.domain.FirebaseLog;
import com.allknu.backend.repository.FirebaseLogRepository;
import com.allknu.backend.web.dto.ResponseFcm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test") // 테스트서버 프로파일 적용
public class FCMApiServiceTests {
    @Autowired
    private FCMApiService fcmApiService;
    @Autowired
    private FirebaseLogRepository firebaseLogRepository;

    @Transactional
    @DisplayName("로그 조회 pageable 테스트")
    @Test
    void getLogsPageableTest() {
        FirebaseLog log = FirebaseLog.builder()
                .adminEmail("myEmail")
                .title("hello")
                .body("world")
                .link("https://naver.com")
                .build();
        firebaseLogRepository.save(log);
        log = FirebaseLog.builder()
                .adminEmail("myEmail2")
                .title("hello2")
                .body("world2")
                .link("https://naver.com")
                .build();
        firebaseLogRepository.save(log);
        log = FirebaseLog.builder()
                .adminEmail("myEmail3")
                .title("hello")
                .body("world2")
                .link("https://naver.com")
                .build();
        firebaseLogRepository.save(log);
        log = FirebaseLog.builder()
                .adminEmail("myEmail4")
                .title("hello2")
                .body("world2")
                .link("https://naver.com")
                .build();
        firebaseLogRepository.save(log);
        log = FirebaseLog.builder()
                .adminEmail("myEmail5")
                .title("hello2")
                .body("world2")
                .link("https://naver.com")
                .build();
        firebaseLogRepository.save(log);

        Pageable pageable = PageRequest.of(0, 2);
        Page<ResponseFcm.Log> logs = fcmApiService.getAllFcmLog(pageable).orElseGet(()->null);
        assertNotNull(logs);

        for(ResponseFcm.Log item : logs) {
            System.out.println(item.getAdminEmail());
            System.out.println(item.getTimestamp());
        }
    }
}
