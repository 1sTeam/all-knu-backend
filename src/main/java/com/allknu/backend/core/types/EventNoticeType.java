package com.allknu.backend.core.types;


import lombok.Getter;

@Getter
public enum EventNoticeType {
    ALL("전체", 0),
    CAMPUS("교내", 119),
    SUBURBS("교외", 8481);

    private String type;
    private int searchMenuNumber;
    EventNoticeType(String type, int searchMenuNumber) {
        this.type = type;
        this.searchMenuNumber = searchMenuNumber;
    }
}
