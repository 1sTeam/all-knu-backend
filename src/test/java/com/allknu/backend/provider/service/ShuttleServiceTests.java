package com.allknu.backend.provider.service;

import com.allknu.backend.core.service.ShuttleService;
import com.allknu.backend.entity.Station;
import com.allknu.backend.entity.StationTimetable;
import com.allknu.backend.exception.errors.NotFoundStationException;
import com.allknu.backend.exception.errors.StationTimeDuplicatedException;
import com.allknu.backend.repository.StationRepository;
import com.allknu.backend.repository.StationTimetableRepository;
import com.allknu.backend.web.dto.RequestStationTimetable;
import com.allknu.backend.web.dto.ResponseStation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class ShuttleServiceTests {

    @Autowired
    ShuttleService shuttleService;
    @Autowired
    StationRepository stationRepository;
    @Autowired
    StationTimetableRepository stationTimetableRepository;


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
        List<String> stationList = shuttleService.getAllStation();
        assertNotNull(stationList);
    }

    @DisplayName("정류장 조회 성공 테스트(정류장이 없을 경우)")
    @Transactional
    @Test
    void listStationTestWhenNotExistStation(){
        //정류장 조회
        List<String> stationList = shuttleService.getAllStation();
        assertNotNull(stationList);
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

    @DisplayName("정류장 시간표 등록 성공 테스트")
    @Transactional
    @Test
    void registerStationTimetableTest() throws ParseException{
        //정류장 등록
        Station station = Station.builder()
                .station("기흥역")
                .build();
        station = stationRepository.save(station);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date time = format.parse("08:10:00");
        //시간표 등록
        RequestStationTimetable.addStationTime stationTime = RequestStationTimetable.addStationTime.builder()
                .station("기흥역")
                .time(time)
                .build();
        shuttleService.registerStationTimetable("기흥역", stationTime.getTime());

        List<StationTimetable> stationTimetableList =stationTimetableRepository.findByStation(station);
        assertNotNull(stationTimetableList);
    }

    @DisplayName("정류장 시간표 등록 실패 테스트(시간 중복)")
    @Transactional
    @Test
    void registerStationTimetableWhenTimeDuplicatedTest() throws ParseException{
        //정류장 등록
        Station station = Station.builder()
                .station("기흥역")
                .build();
        station = stationRepository.save(station);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date time = format.parse("08:10:00");
        //시간표 등록
        RequestStationTimetable.addStationTime stationTime = RequestStationTimetable.addStationTime.builder()
                .station("기흥역")
                .time(time)
                .build();
        shuttleService.registerStationTimetable("기흥역", stationTime.getTime());

        assertThrows(StationTimeDuplicatedException.class, ()-> shuttleService.registerStationTimetable("기흥역", stationTime.getTime()));
    }

    @DisplayName("시간표 조회 성공 테스트")
    @Transactional
    @Test
    void listTimetableTest() throws ParseException{
        //정류장 등록
        Station station = Station.builder()
                .station("기흥역")
                .build();
        station = stationRepository.save(station);
        //시간 입력
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date time = format.parse("08:10:00");
        Date time1 = format.parse("08:20:00");
        StationTimetable stationTimetable = StationTimetable.builder()
                .station(station)
                .stopTime(time)
                .build();
        stationTimetableRepository.save(stationTimetable);
        station.addTimetable(stationTimetable);
        StationTimetable stationTimetable1 = StationTimetable.builder()
                .station(station)
                .stopTime(time1)
                .build();
        stationTimetableRepository.save(stationTimetable1);
        station.addTimetable(stationTimetable1);
        //시간표 조회
        List<ResponseStation.stationTime> timeList = shuttleService.getAllStationTimetable();
        assertNotNull(timeList);
    }
}
