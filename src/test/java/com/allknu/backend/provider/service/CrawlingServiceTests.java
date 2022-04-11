package com.allknu.backend.provider.service;


import com.allknu.backend.core.types.EventNoticeType;
import com.allknu.backend.core.types.MajorNoticeType;
import com.allknu.backend.core.types.UnivNoticeType;
import com.allknu.backend.web.dto.ResponseCrawling;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test") // 테스트서버 프로파일 적용
public class CrawlingServiceTests {

    @Autowired
    private CrawlingService crawlingService;

    @Test
    @DisplayName("공지사항 크롤링 테스트")
    void getUnivNoticeTest() {
        List<ResponseCrawling.UnivNotice> notices = crawlingService.getUnivNotice(1, UnivNoticeType.ALL).orElseGet(()->null);
        for(int i = 0 ; i < notices.size() ; i++) {
            ResponseCrawling.UnivNotice notice = notices.get(i);
            System.out.println(notice.getTitle() + notice.getDate() + notice.getViews() + notice.getLink());
        }
    }
    @Test
    @DisplayName("행사/안내 크롤링 테스트")
    void getEventNoticeTest() {
        List<ResponseCrawling.EventNotice> eventNotice = crawlingService.getEventNotice(3, EventNoticeType.SUBURBS).orElseGet(()->null);
        for(int i = 0 ; i < eventNotice.size() ; i++) {
            ResponseCrawling.EventNotice eventNoticeResult = eventNotice.get(i);
            System.out.println(eventNoticeResult.getTitle()+ "\n" + eventNoticeResult.getDate() + "\n" + eventNoticeResult.getViews() + "\n" + eventNoticeResult.getLink()
                    + "\n" + eventNoticeResult.getDepartment() + "\n" + eventNoticeResult.getTel() + "\n" + eventNoticeResult.getWriter() + "\n-----------------------------------------------\n\n");
        }
    }

    @Test
    @DisplayName("기본 강남대 템플릿을 사용하는 학과 공지사항 크롤링 테스트")
    void getMajorNoticeTest() {
        //EnumSet 이용해 모든 타입 단위테스트
        EnumSet.allOf(MajorNoticeType.class)
                .forEach(type -> {
                    System.out.println(type.getKorean() + "시작하겠습니다!");
                    List<ResponseCrawling.UnivNotice> notices = crawlingService.getMajorDefaultTemplateNotice(1, type).orElseGet(()->null);

                    assertNotNull(notices);

                    for(int i = 0 ; i < notices.size() ; i++) {
                        ResponseCrawling.UnivNotice notice = notices.get(i);
                        System.out.println(notice.getTitle() + notice.getDate() + notice.getViews() + notice.getLink());
                    }
                });
    }

    @Test
    @DisplayName("웹사이트에서 학사일정 크롤링 테스트")
    void getCalendar(){
        Map<String, List<ResponseCrawling.Schedule>> list = crawlingService.getKnuCalendar().orElseGet(()->null);
        System.out.println(list);

    }
}
