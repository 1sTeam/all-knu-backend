package com.allknu.backend.knuapi.domain;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MajorNoticeTypeTests {

    @Test
    @DisplayName("학과를 찾는다.")
    void findMajorTest() {
        String target = "소프트웨어응용학부";

        MajorNoticeType type = MajorNoticeType.findByMajor(target);
        assertEquals(type, MajorNoticeType.SOFTWARE);

        target = "글로벌경영학부";
        type = MajorNoticeType.findByMajor(target);
        assertEquals(type, MajorNoticeType.GLOBAL_BIZ);
    }

    @Test
    @DisplayName("학과를 찾지 못하면 디폴트로 SOFTWARE을 준다.")
    void ifNotFindMajorThenGiveSOFTWARETest() {
        String target = "난없어학과";

        MajorNoticeType type = MajorNoticeType.findByMajor(target);
        assertEquals(type, MajorNoticeType.SOFTWARE);
    }
}
