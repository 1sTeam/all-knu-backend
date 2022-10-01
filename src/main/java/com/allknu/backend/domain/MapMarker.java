package com.allknu.backend.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name="map_marker")
@Entity
@Getter
@NoArgsConstructor
public class MapMarker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "map_marker_type")
    @Enumerated(EnumType.STRING)
    private MapMarkerType mapMarkerType;
    @Column(name = "title")
    private String title;
    @Column(name = "sub_title")
    private String subTitle;
    @Column(name = "floor")
    private String floor;
    @Column(name = "room")
    private String room;
    @Column(name = "name")
    private String name;
    @Column(name = "icon")
    private String icon;
    @Column(name = "image")
    private String image;
    @Embedded
    private GPSLocation gpsLocation;

    // time, phone처럼 운영 시간 관련 1대1 매핑 info
    @OneToOne(mappedBy = "mapMarker", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private MapMarkerOperationInfo mapMarkerOperationInfo;

    @Builder
    public MapMarker(MapMarkerType mapMarkerType, String title, String subTitle, String floor, String room, String name, String icon, String image, GPSLocation gpsLocation) {
        this.mapMarkerType = mapMarkerType;
        this.title = title;
        this.subTitle = subTitle;
        this.floor = floor;
        this.room = room;
        this.name = name;
        this.icon = icon;
        this.image = image;
        this.gpsLocation = gpsLocation;
    }
    // 운영 정보가 있다면 1대1 매핑 시켜준다.
    public void mappingOperationInfo(MapMarkerOperationInfo mapMarkerOperationInfo) {
        mapMarkerOperationInfo.setMapMarker(this);
        this.mapMarkerOperationInfo = mapMarkerOperationInfo;
    }
}
