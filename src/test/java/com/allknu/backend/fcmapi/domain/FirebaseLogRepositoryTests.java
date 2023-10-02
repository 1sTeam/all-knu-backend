package com.allknu.backend.fcmapi.domain;

import com.allknu.backend.fcmapi.domain.FirebaseLog;
import com.allknu.backend.fcmapi.domain.FirebaseLogRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class FirebaseLogRepositoryTests {
    @Autowired
    private FirebaseLogRepository firebaseLogRepository;

    @Transactional
    @DisplayName("로그 등록 테스트")
    @Test
    void registerLogTest() {
        FirebaseLog log = FirebaseLog.builder()
                .adminEmail("myEmail")
                .title("hello")
                .body("world")
                .link("https://naver.com")
                .build();
        log = firebaseLogRepository.save(log);

        FirebaseLog registered = firebaseLogRepository.findById(log.getId()).orElseGet(()->null);
        assertNotNull(registered);
        System.out.println(registered.getAdminEmail());
        System.out.println(registered.getId());
        System.out.println(registered.getTitle());
        System.out.println(registered.getBody());
        System.out.println(registered.getTimestamp());
    }

    @Transactional
    @DisplayName("로그 조회 테스트")
    @Test
    void getLogsTest() {
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

        List<FirebaseLog> logs = firebaseLogRepository.findAll();
        assertNotNull(logs);
        for(FirebaseLog item : logs) {
            System.out.println(item.getAdminEmail());
        }
    }
}
