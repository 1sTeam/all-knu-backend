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
    void registerStationTimetableV2Test() throws ParseException {
        // 정류장 등록
        Station station = Station.builder()
                .station("기흥역") // station의 필드명을 확인하세요. 예제에서는 stationName으로 가정합니다.
                .build();
        station = stationRepository.save(station);

        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date time = format.parse("08:10:00");

        shuttleService.registerStationTimetable(station.getStation(), String.valueOf(StationTimetable.DayOfWeek.월요일), time, "이공관");

        List<StationTimetable> stationTimetableList = stationTimetableRepository.findByStation(station);
        assertNotNull(stationTimetableList);
    }

    @DisplayName("시간표 조회 성공 테스트")
    @Transactional
    @Test
    void getTimetableTest() throws ParseException {
        // 정류장 등록
        Station station = Station.builder()
                .station("기흥역")
                .build();
        station = stationRepository.save(station);

        // 시간 입력
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date time = format.parse("08:10:00");
        Date time1 = format.parse("08:20:00");

        // 시간표 등록
        StationTimetable stationTimetable = StationTimetable.builder()
                .station(station)
                .dayOfWeek(StationTimetable.DayOfWeek.월요일)
                .stopTime(time)
                .destination("이공관")
                .build();
        stationTimetableRepository.save(stationTimetable);

        StationTimetable stationTimetable1 = StationTimetable.builder()
                .station(station)
                .dayOfWeek(StationTimetable.DayOfWeek.금요일)
                .stopTime(time1)
                .destination("사훈")
                .build();
        stationTimetableRepository.save(stationTimetable1);

        // 금요일 시간표 조회
        List<StationTimetable> fridayTimetables = stationTimetableRepository.findByStationAndDayOfWeekOrderByStopTimeAsc( station,StationTimetable.DayOfWeek.금요일);
        assertNotNull(fridayTimetables);

        // 검증
        assertTrue(fridayTimetables.size() > 0);
        StationTimetable firstFridayTimetable = fridayTimetables.get(0);
        assertEquals(station, firstFridayTimetable.getStation());
        assertEquals("08:20:00", format.format(firstFridayTimetable.getStopTime()));
        assertEquals("사훈", firstFridayTimetable.getDestination());
    }
    @DisplayName("시간표 삭제 성공 테스트")
    @Transactional
    @Test
    void deleteTimetableTest() throws ParseException {
        // 정류장 등록
        Station station = Station.builder()
                .station("기흥역")
                .build();
        station = stationRepository.save(station);

        // 시간 입력
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date time = format.parse("08:10:00");

        // 시간표 등록
        StationTimetable stationTimetable = StationTimetable.builder()
                .station(station)
                .dayOfWeek(StationTimetable.DayOfWeek.월요일)
                .stopTime(time)
                .build();
        stationTimetable = stationTimetableRepository.save(stationTimetable);

        // 시간표 삭제
        stationTimetableRepository.delete(stationTimetable);

        // 삭제 확인
        StationTimetable deletedTimetable = stationTimetableRepository.findByStationAndDayOfWeekAndStopTime(station, StationTimetable.DayOfWeek.월요일, time);
        assertNull(deletedTimetable, "시간표가 삭제되었음을 확인합니다.");

        // 리스트에서 삭제된 시간표 확인
        List<StationTimetable> remainingTimetables = stationTimetableRepository.findByStationAndDayOfWeekOrderByStopTimeAsc(stationTimetable.getStation(),StationTimetable.DayOfWeek.월요일);
        assertFalse(remainingTimetables.contains(stationTimetable), "리스트에서 삭제된 시간표를 확인합니다.");
    }
}
