package com.allknu.backend.knuapi.domain;

import lombok.Getter;

@Getter
public enum UnivNoticeType {
    ALL("전체", 0),
    ACADEMIC("학사", 116),
    SCHOLARSHIP("장학", 117),
    LEARNING("학습/상담", 118),
    EMPLOY("취창업", 344);

    private String type;
    private int searchMenuNumber;
    UnivNoticeType(String type, int searchMenuNumber) {
        this.type = type;
        this.searchMenuNumber = searchMenuNumber;
    }
}
