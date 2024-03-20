package com.allknu.backend.map.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

/**
 * MapMarker 와 1대1 매핑이되는 운영시간 관련 추가 정보
 */
@Table(name="map_marker_operation_info")
@Entity
@Getter
@NoArgsConstructor
public class MapMarkerOperationInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "operation_time")
    private String operationTime;
    @Column(name = "phone")
    private String phone;

    @OneToOne
    @JoinColumn(name = "map_marker_id")
    private MapMarker mapMarker;

    @Builder
    public MapMarkerOperationInfo(String operationTime, String phone) {
        this.operationTime = operationTime;
        this.phone = phone;
    }

    public void setMapMarker(MapMarker mapMarker) {
        this.mapMarker = mapMarker;
    }
}
