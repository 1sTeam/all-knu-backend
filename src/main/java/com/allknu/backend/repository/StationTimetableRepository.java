package com.allknu.backend.repository;

import com.allknu.backend.entity.Station;
import com.allknu.backend.entity.StationTimetable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface StationTimetableRepository extends JpaRepository<StationTimetable, Long> {
    List<StationTimetable> findByStation(Station station);
    StationTimetable findByStationAndStopTime(Station station, Date stopTime);
    StationTimetable findByStopTime(Date stopTime);
}
