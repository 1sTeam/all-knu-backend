package com.allknu.backend.core.service;

import java.util.List;

public interface ShuttleService {
    void registerStation(String stationname);
    List<String> listStation();
    void deleteStation(String stationName);
}
