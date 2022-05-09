package com.allknu.backend.provider.service;

import com.allknu.backend.core.service.ShuttleService;
import com.allknu.backend.entity.Station;
import com.allknu.backend.repository.StationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class ShuttleServiceTests {

    @Autowired
    ShuttleService shuttleService;
    @Autowired
    StationRepository stationRepository;

    @DisplayName("정류장 등록 성공 테스트")
    @Transactional
    @Test
    void registerStation(){
        shuttleService.registerStation("기흥역");
        Station station = stationRepository.findByStation("기흥역");
        assertNotNull(station);
    }
}
