package com.allknu.backend.shttle.application;

import com.allknu.backend.shuttle.application.ShuttleService;
import com.allknu.backend.shuttle.domain.*;
import com.allknu.backend.global.exception.errors.NotFoundStationException;
import com.allknu.backend.shuttle.application.dto.ResponseStation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    @Autowired
    DayRepository dayRepository;


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
    void registerStationTimetableV2Test() throws ParseException{
        //정류장 등록
        Station station = Station.builder()
                .station("기흥역")
                .build();
        station = stationRepository.save(station);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date time = format.parse("08:10:00");
        //시간표 등록
        shuttleService.registerStationTimetable("기흥역","월요일", time, "이공관");

        List<StationTimetable> stationTimetableList = stationTimetableRepository.findByStation(station);
        assertNotNull(stationTimetableList);
    }

    @DisplayName("시간표 조회 성공 테스트")
    @Transactional
    @Test
    void getTimetableV2Test() throws ParseException{
        //정류장 등록
        Station station = Station.builder()
                .station("기흥역")
                .build();
        station = stationRepository.save(station);
        //시간 입력
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date time = format.parse("08:10:00");
        Date time1 = format.parse("08:20:00");

        // 요일 입력
        Day day = new Day(null, "월요일", new ArrayList<>());
        day = dayRepository.save(day);

        StationTimetable stationTimetable = StationTimetable.builder()
                .station(station)
                .day(day)
                .stopTime(time)
                .destination("이공관")
                .build();
        stationTimetableRepository.save(stationTimetable);
        station.addTimetable(stationTimetable);

        StationTimetable stationTimetable1 = StationTimetable.builder()
                .station(station)
                .day(day)
                .stopTime(time1)
                .destination("사훈")
                .build();
        stationTimetableRepository.save(stationTimetable1);
        station.addTimetable(stationTimetable1);

        //시간표 조회
        List<ResponseStation.getStationTime> timeList = shuttleService.getStationTimetable(day.getName());
        assertNotNull(timeList);

        // 검증
        assertTrue(timeList.size() > 0);
        assertEquals("기흥역", timeList.get(0).getStation());
        assertEquals(2, timeList.get(0).getStopTime().size());
        assertEquals("08:10:00", format.format(timeList.get(0).getStopTime().get(0).getTime()));
        assertEquals("08:20:00", format.format(timeList.get(0).getStopTime().get(1).getTime()));
        assertEquals("이공관", timeList.get(0).getStopTime().get(0).getDestination());
        assertEquals("사훈", timeList.get(0).getStopTime().get(1).getDestination());
    }


    @DisplayName("시간표 삭제 성공 테스트")
    @Transactional
    @Test
    void deleteTimetableTest() throws ParseException{
        //정류장 등록
        Station station = Station.builder()
                .station("기흥역")
                .build();

        // 요일 입력
        Day day = new Day(null, "월요일", new ArrayList<>());
        day = dayRepository.save(day);

        station = stationRepository.save(station);
        //시간 입력
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date time = format.parse("08:10:00");
        StationTimetable stationTimetable = StationTimetable.builder()
                .station(station)
                .day(day)
                .stopTime(time)
                .build();
        stationTimetableRepository.save(stationTimetable);
        station.addTimetable(stationTimetable);
        //시간표 조회
        shuttleService.deleteStationTimetable("기흥역", "월요일", time);
        StationTimetable stationTime = stationTimetableRepository.findByStationAndDayAndStopTime(station,day,time);
        assertNull(stationTime);

    }
}
