package com.allknu.backend.knuapi.application;


import com.allknu.backend.knuapi.application.dto.CalendarResponseDto;
import com.allknu.backend.knuapi.domain.EventNoticeType;
import com.allknu.backend.knuapi.domain.MajorNoticeType;
import com.allknu.backend.knuapi.domain.UnivNoticeType;
import com.allknu.backend.knuapi.application.dto.ResponseCrawling;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.EnumSet;
import java.util.List;

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
        List<ResponseCrawling.EventNotice> eventNotice = crawlingService.getEventNotice(1, EventNoticeType.ALL).orElseGet(()->null);
        for(int i = 0 ; i < eventNotice.size() ; i++) {
            ResponseCrawling.EventNotice eventNoticeResult = eventNotice.get(i);
            System.out.println(eventNoticeResult.getTitle()+ "\n" + eventNoticeResult.getDate() + "\n" + eventNoticeResult.getViews() + "\n" + eventNoticeResult.getLink()
                    + "\n" + eventNoticeResult.getWriter());
        }
    }

    @Test
    @DisplayName("기본 강남대 템플릿을 사용하는 학과 공지사항 크롤링 테스트")
    void getMajorNoticeTest() {
        //EnumSet 이용해 모든 타입 단위테스트
        EnumSet.allOf(MajorNoticeType.class)
                .forEach(type -> {
                    System.out.println(type.getKorean() + "공지사항 출력");
                    List<ResponseCrawling.UnivNotice> notices = crawlingService.getMajorDefaultTemplateNotice(1, type).orElseGet(()->null);

                    assertNotEquals(notices.size(), 0);

                    for(int i = 0 ; i < notices.size() ; i++) {
                        ResponseCrawling.UnivNotice notice = notices.get(i);
                        System.out.println("--------item--------");
                        System.out.println("notice.getTitle(): " + notice.getTitle());
                        System.out.println("notice.getDate(): " + notice.getDate());
                        System.out.println("notice.getViews(): " + notice.getViews());
                        System.out.println("notice.getLink(): " + notice.getLink());
                        System.out.println("notice.getWriter(): " + notice.getWriter());
                        System.out.println("notice.getNumber(): " + notice.getNumber());
                        System.out.println("------------------");
                    }
                });
    }

    @Test
    @DisplayName("웹사이트에서 학사일정 크롤링 테스트")
    void getCalendar(){
        CalendarResponseDto calendar = crawlingService.getKnuCalendar();
        Assertions.assertNotEquals(0, calendar.getCalendarMap().size());
    }
}
