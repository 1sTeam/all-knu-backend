package com.allknu.backend.shuttle.domain;

import com.allknu.backend.shuttle.domain.Station;
import com.allknu.backend.shuttle.domain.StationTimetable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface StationTimetableRepository extends JpaRepository<StationTimetable, Long> {
    List<StationTimetable> findByStation(Station station);
    List<StationTimetable> findByStationOrderByStopTimeAsc(Station station);
    StationTimetable findByStationAndStopTime(Station station, Date stopTime);
    StationTimetable findByStationAndStopTimeAndDestination(Station station, Date stopTime, String destination);
    StationTimetable findByStopTime(Date stopTime);
}
