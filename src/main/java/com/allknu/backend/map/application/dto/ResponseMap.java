package com.allknu.backend.map.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public class ResponseMap {
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetMapMarker{
        private Long id;
        private String type;
        private String title;
        private String subTitle;
        private String floor;
        private String room;
        private String name;
        private String icon;
        private String image;
        private ResponseMap.Location location;
        private ResponseMap.Info more;
        private ResponseMap.Info info;

    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Location {
        private Double lat;
        private Double lon;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Info {
        private String time;
        private String phone;
    }
}
