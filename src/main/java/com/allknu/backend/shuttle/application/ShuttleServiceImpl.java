package com.allknu.backend.shuttle.application;

import com.allknu.backend.global.exception.errors.*;
import com.allknu.backend.shuttle.domain.*;
import com.allknu.backend.shuttle.application.dto.ResponseStation;
import com.allknu.backend.shuttle.application.dto.ResponseStationTimetable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShuttleServiceImpl implements ShuttleService {
    private final StationRepository stationRepository;
    private final StationTimetableRepository stationTimetableRepository;
    private final DayRepository dayRepository;

    @Transactional
    @Override
    public void registerStation(String stationName){
        //정거장 존재 확인
        Station station = stationRepository.findByStation(stationName);
        if(station != null){ //이미 존재하면
            throw new StationNameDuplicatedException();
        }
        station = Station.builder()
                .station(stationName)
                .build();
        //정거장 등록
        stationRepository.save(station);
    }

    @Transactional
    @Override
    public List<String> getAllStation(){
        List<Station> stationList = stationRepository.findAll();
        List<String> stationNameList = new ArrayList<>();

        if(!stationList.isEmpty()){
            for(Station station : stationList){
                stationNameList.add(station.getStation());
            }
        }

        return stationNameList;
    }

    @Transactional
    @Override
    public void deleteStation(String stationName){
        //정거장 존재 확인
        Station station = stationRepository.findByStation(stationName);
        if(station == null){ //정거장이 없으면
            throw new NotFoundStationException();
        }
        stationRepository.delete(station);
    }

    @Transactional
    @Override
    public void registerStationTimetable(String stationName, String dayName, Date stopTime, String destination){
        //정거장 엔티티 꺼내기
        Station station = stationRepository.findByStation(stationName);
        if(station == null){
            throw new NotFoundStationException();
        }
        Day day = dayRepository.findByName(dayName);
        StationTimetable stationTime = stationTimetableRepository.findByStationAndDayAndStopTimeAndDestination(station, day, stopTime, destination);
        if(stationTime != null){// 이미 있는 시간일 경우
            throw new StationTimeDuplicatedException();
        }
        //시간 입력
        StationTimetable stationTimetable = StationTimetable.builder()
                .station(station)
                .day(day)
                .stopTime(stopTime)
                .destination(destination)
                .build();
        stationTimetableRepository.save(stationTimetable);
        station.addTimetable(stationTimetable);
    }

    @Transactional
    @Override
    public List<ResponseStation.getStationTime> getStationTimetable(String day){
        List<ResponseStation.getStationTime> list = new ArrayList<>();
        List<Station> stationList = stationRepository.findAll();

        if(!stationList.isEmpty()){
            for(Station station : stationList){
                // Day 엔티티를 사용하여 해당일에 맞는 시간표만 조회
                List<StationTimetable> times = stationTimetableRepository.findByStationAndDayOrderByStopTimeAsc(station, dayRepository.findByName(day));
                List<ResponseStationTimetable> stopTimeList = new ArrayList<>();
                for(StationTimetable stationTimetable : times){
                    ResponseStationTimetable response = ResponseStationTimetable.builder()
                            .time(stationTimetable.getStopTime())
                            .destination(stationTimetable.getDestination())
                            .build();
                    stopTimeList.add(response);
                }

                ResponseStation.getStationTime responseDto = ResponseStation.getStationTime.builder()
                        .station(station.getStation())
                        .stopTime(stopTimeList)
                        .build();
                list.add(responseDto);
            }
        }
        return list;
    }

    @Transactional
    @Override
    public void deleteStationTimetable(String stationName, String dayName, Date stopTime){
        //정거장 엔티티 꺼내기
        Station station = stationRepository.findByStation(stationName);
        if(station == null){
            throw new NotFoundStationException();
        }

        // 요일 엔티티 꺼내기
        Day day = dayRepository.findByName(dayName);
        if(day == null){
            throw new NotFoundDayException();
        }

        StationTimetable stationTime = stationTimetableRepository.findByStationAndDayAndStopTime(station, day, stopTime);
        if(stationTime == null){// 존재하지 않는 시간일 경우
            throw new NotFoundStationTimetableException();
        }
        //시간표 삭제
        stationTimetableRepository.delete(stationTime);
    }
}
