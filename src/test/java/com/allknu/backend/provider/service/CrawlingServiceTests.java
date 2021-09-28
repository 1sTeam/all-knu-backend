package com.allknu.backend.provider.service;


import com.allknu.backend.core.types.UnivNoticeType;
import com.allknu.backend.web.dto.ResponseCrawling;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("local")

public class CrawlingServiceTests {

    @Autowired
    private CrawlingService crawlingService;

    @Test
    @DisplayName("공지사항 크롤링 테스트")
    void getUnivNoticeTest() {
        List<ResponseCrawling.UnivNotice> notices = crawlingService.getUnivNotice(1, UnivNoticeType.ALL).orElseGet(()->null);
        for(int i = 0 ; i < notices.size() ; i++) {
            ResponseCrawling.UnivNotice notice = notices.get(i);
            System.out.println(notice.getTitle() + notice.getLink() + notice.getDate() + notice.getViews());
        }
    }
}
