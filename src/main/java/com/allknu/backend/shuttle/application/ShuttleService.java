package com.allknu.backend.shuttle.application;

import com.allknu.backend.shuttle.application.dto.ResponseStation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface ShuttleService {
    void registerStation(String stationName);
    List<String> getAllStation();
    void deleteStation(String stationName);
    void registerStationTimetable(String stationName, String dayName, Date stopTime, String destination);
    List<ResponseStation.getStationTime> getStationTimetable(String day);
    void deleteStationTimetable(String stationName, String dayName, Date stopTime);
}
