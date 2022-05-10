package com.allknu.backend.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "station")
@Entity
@Getter
@NoArgsConstructor
public class Station {
    @Id
    @Column(name = "station_name")
    private String station;

    @OneToMany(mappedBy = "station")
    private List<StationTimetable> stationTimetableList = new ArrayList<>(); // 시간표 리스트

    @Builder
    public Station(String station){
        this.station = station;
    }
}
