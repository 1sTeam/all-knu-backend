package com.allknu.backend.web.dto;

import com.allknu.backend.domain.MapMarkerType;
import lombok.Builder;
import lombok.Data;


public class ResponseMap {
    @Builder
    @Data
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
    public static class Location {
        private Double lat;
        private Double lon;
    }

    @Builder
    @Data
    public static class Info {
        private String time;
        private String phone;
    }
}
