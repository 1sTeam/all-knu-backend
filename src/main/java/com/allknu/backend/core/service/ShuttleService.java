package com.allknu.backend.core.service;

import com.allknu.backend.web.dto.ResponseStation;

import java.util.Date;
import java.util.List;

public interface ShuttleService {
    void registerStation(String stationName);
    List<String> getAllStation();
    void deleteStation(String stationName);
    void registerStationTimetable(String stationName, Date stopTime);
    List<ResponseStation.StationTime> getAllStationTimetable();
    void deleteStationTimetable(String stationName, Date stopTime);
}
