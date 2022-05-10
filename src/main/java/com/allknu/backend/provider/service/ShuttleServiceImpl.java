package com.allknu.backend.provider.service;

import com.allknu.backend.core.service.ShuttleService;
import com.allknu.backend.entity.Station;
import com.allknu.backend.exception.errors.NotFoundStationException;
import com.allknu.backend.exception.errors.StationNameDuplicatedException;
import com.allknu.backend.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.metrics.Stat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShuttleServiceImpl implements ShuttleService {
    private final StationRepository stationRepository;

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
    public List<String> listStation(){
        List<Station> stationList = stationRepository.findAll();
        List<String> stationNameList = new ArrayList<>();

        if(stationList.isEmpty()){//정거장이 없으면
            throw new NotFoundStationException();
        }
        for(Station station : stationList){
            stationNameList.add(station.getStation());
        }
        return stationNameList;
    }
}
