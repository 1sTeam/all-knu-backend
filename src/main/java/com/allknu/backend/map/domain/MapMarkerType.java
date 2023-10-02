package com.allknu.backend.map.domain;

import lombok.Getter;

@Getter
public enum MapMarkerType {
    NORMAL("normal"),
    DETAIL("detail"),
    SUMMARY("summary");
    private String value;
    MapMarkerType(String value) {
        this.value = value;
    }
}
