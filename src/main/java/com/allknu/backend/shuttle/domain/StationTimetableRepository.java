package com.allknu.backend.shuttle.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface StationTimetableRepository extends JpaRepository<StationTimetable, Long> {
    List<StationTimetable> findByStation(Station station);
    StationTimetable findByStationAndDayOfWeekAndStopTimeAndDestination(Station station, StationTimetable.DayOfWeek dayOfWeek, Date stopTime, String destination);
    List<StationTimetable> findByStationAndDayOfWeekOrderByStopTimeAsc(Station station, StationTimetable.DayOfWeek dayOfWeek);
    StationTimetable findByStationAndDayOfWeekAndStopTime(Station station,StationTimetable.DayOfWeek dayOfWeek, Date stopTime);
}
