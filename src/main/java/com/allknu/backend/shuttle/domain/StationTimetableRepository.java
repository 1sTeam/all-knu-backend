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
    StationTimetable findByStationAndDayAndStopTimeAndDestination(Station station, Day day, Date stopTime, String destination);
    List<StationTimetable> findByStationAndDayOrderByStopTimeAsc(Station station, Day day);
    StationTimetable findByStopTime(Date stopTime);
    StationTimetable findByStationAndDayAndStopTime(Station station, Day day, Date stopTime);
}
