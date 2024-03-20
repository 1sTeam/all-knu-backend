package com.allknu.backend.map.application.dto;

import com.allknu.backend.map.domain.MapMarkerType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

public class RequestMap {
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateMarker {
        @NotNull(message = "타입이 비었음")
        private MapMarkerType type;
        @NotNull(message = "타이틀이 비었음")
        private String title;
        @NotNull(message = "서브타이틀이 비었음")
        private String subTitle;
        @NotNull(message = "floor 비었음")
        private String floor;
        @NotNull(message = "room 비었음")
        private String room;
        @NotNull(message = "name 비었음")
        private String name;
        @NotNull(message = "icon 비었음")
        private String icon;
        @NotNull(message = "image 비었음")
        private String image;
        @NotNull(message = "gps 비었음")
        private GPSInfo gpsInfo;

        private OperationInfo operationInfo; // nullable
    }
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GPSInfo {
        @NotNull(message = "latitude 비었음")
        private Double latitude;
        @NotNull(message = "longitude 비었음")
        private Double longitude;
    }
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OperationInfo {
        private String operationTime;
        private String phone;
    }

}
