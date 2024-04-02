package com.allknu.backend.shuttle.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.Date;

@Table(name = "station_timetable")
@Getter
@Entity
@NoArgsConstructor
public class StationTimetable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stop_time")
    @Temporal(TemporalType.TIME)
    private Date stopTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_name")
    private Station station;

    @Column(name = "destination")
    private String destination;


    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week")
    private DayOfWeek dayOfWeek;

    @Builder
    public StationTimetable(Station station, DayOfWeek dayOfWeek, Date stopTime, String destination){
        this.station = station;
        this.dayOfWeek = dayOfWeek;
        this.stopTime = stopTime;
        this.destination = destination;
    }
    public enum DayOfWeek {
        월요일, 화요일, 수요일, 목요일, 금요일, 토요일, 일요일
    }

}
