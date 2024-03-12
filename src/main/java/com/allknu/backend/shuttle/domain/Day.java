package com.allknu.backend.shuttle.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table(name = "station_time_table_day")
@Getter
@Entity
@NoArgsConstructor
public class Day {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "day", cascade = CascadeType.ALL)
    private List<StationTimetable> stationTimetables = new ArrayList<>();

    public Day(Long id, String name, List<StationTimetable> stationTimetables) {
        this.id = id;
        this.name = name;
        this.stationTimetables = stationTimetables;
    }
}
