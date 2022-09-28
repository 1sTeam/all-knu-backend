package com.allknu.backend.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

// GPS 정보에 대한 임베디드 타입
@Embeddable
@Getter
@NoArgsConstructor // 임베디드는 기본생성자가 있어야함
public class GPSLocation {
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;
    @Builder
    public GPSLocation(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
