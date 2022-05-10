package com.allknu.backend.provider.service;

import com.allknu.backend.core.service.ShuttleService;
import com.allknu.backend.entity.Station;
import com.allknu.backend.exception.errors.NotFoundStationException;
import com.allknu.backend.repository.StationRepository;
import org.apache.kafka.common.metrics.Stat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    void registerStationTest(){
        shuttleService.registerStation("기흥역");
        Station station = stationRepository.findByStation("기흥역");
        assertNotNull(station);
    }

    @DisplayName("정류장 조회 성공 테스트")
    @Transactional
    @Test
    void listStationTest(){
        //정류장 등록
        Station station = Station.builder()
                .station("기흥역")
                .build();
        stationRepository.save(station);
        Station station1 = Station.builder()
                .station("이공관")
                .build();
        stationRepository.save(station);
        //정류장 조회
        List<String> stationList = shuttleService.listStation();
        assertNotNull(stationList);
    }

    @DisplayName("정류장 조회 실패 테스트(정류장이 없을 경우)")
    @Transactional
    @Test
    void listStationTestWhenNotExistStation(){
        //정류장 조회
        assertThrows(NotFoundStationException.class, ()-> shuttleService.listStation());
    }
    @DisplayName("정류장 삭제 성공 테스트")
    @Transactional
    @Test
    void deleteStationTest(){
        //정류장 등록
        Station station = Station.builder()
                .station("기흥역")
                .build();
        stationRepository.save(station);
        Station station1 = Station.builder()
                .station("이공관")
                .build();
        stationRepository.save(station);
        //정류장 삭제
        shuttleService.deleteStation("기흥역");
        Station station2 = stationRepository.findByStation("기흥역");
        assertNull(station2);
    }

    @DisplayName("정류장 삭제 실패 테스트(정류장이 없을 경우)")
    @Transactional
    @Test
    void deleteStationTestWhenNotExistStation(){
        //정류장 삭제
        assertThrows(NotFoundStationException.class, ()-> shuttleService.deleteStation("서울역"));
    }
}
