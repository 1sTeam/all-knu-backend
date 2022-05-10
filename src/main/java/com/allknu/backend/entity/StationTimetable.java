package com.allknu.backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
}
