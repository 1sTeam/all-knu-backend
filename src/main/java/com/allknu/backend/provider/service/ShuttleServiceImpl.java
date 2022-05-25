package com.allknu.backend.provider.service;

import com.allknu.backend.core.service.ShuttleService;
import com.allknu.backend.entity.Station;
import com.allknu.backend.entity.StationTimetable;
import com.allknu.backend.exception.errors.NotFoundStationException;
import com.allknu.backend.exception.errors.NotFoundStationTimetableException;
import com.allknu.backend.exception.errors.StationNameDuplicatedException;
import com.allknu.backend.exception.errors.StationTimeDuplicatedException;
import com.allknu.backend.repository.StationRepository;
import com.allknu.backend.repository.StationTimetableRepository;
import com.allknu.backend.web.dto.ResponseStation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * 관리자페이지 기능 -> 마이크로서비스 분리 대상
 */
@Service
@RequiredArgsConstructor
public class ShuttleServiceImpl implements ShuttleService {
    private final StationRepository stationRepository;
    private final StationTimetableRepository stationTimetableRepository;

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
    public void registerStationTimetable(String stationName, Date stopTime){
        //정거장 엔티티 꺼내기
        Station station = stationRepository.findByStation(stationName);
        if(station == null){
            throw new NotFoundStationException();
        }
        StationTimetable stationTime = stationTimetableRepository.findByStationAndStopTime(station, stopTime);
        if(stationTime != null){// 이미 있는 시간일 경우
            throw new StationTimeDuplicatedException();
        }
        //시간 입력
        StationTimetable stationTimetable = StationTimetable.builder()
                .station(station)
                .stopTime(stopTime)
                .build();
        stationTimetableRepository.save(stationTimetable);
        station.addTimetable(stationTimetable);
    }

    @Transactional
    @Override
    public List<ResponseStation.StationTime> getAllStationTimetable(){
        List<ResponseStation.StationTime> list = new ArrayList<>();
        //정거장 엔티티 꺼내기
        List<Station> stationList = stationRepository.findAll();
        if(!stationList.isEmpty()){
            for(Station station :stationList){
                List<StationTimetable> times = stationTimetableRepository.findByStationOrderByStopTimeAsc(station);
                List<Date> dates = new ArrayList<>();
                for(StationTimetable stationTimetable : times){
                    dates.add(stationTimetable.getStopTime());
                }
                ResponseStation.StationTime responseDto = ResponseStation.StationTime.builder()
                        .station(station.getStation())
                        .stopTime(dates)
                        .build();
                list.add(responseDto);
            }
        }
        return list;
    }
    @Transactional
    @Override
    public void deleteStationTimetable(String stationName, Date stopTime){
        //정거장 엔티티 꺼내기
        Station station = stationRepository.findByStation(stationName);
        if(station == null){
            throw new NotFoundStationException();
        }
        StationTimetable stationTime = stationTimetableRepository.findByStationAndStopTime(station, stopTime);
        if(stationTime == null){// 존재하지 않는 시간일 경우
            throw new NotFoundStationTimetableException();
        }
        //시간표 삭제
        stationTimetableRepository.delete(stationTime);
    }
}
