package com.allknu.backend.web;


import com.allknu.backend.provider.service.CrawlingService;
import com.allknu.backend.web.dto.CommonResponse;
import com.allknu.backend.web.dto.ResponseCrawling;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CrawlingController {
    private final CrawlingService crawlingService;

    @GetMapping("/crawling/notice/univ/{page}") // 학교 공지사항 크롤링 요청
    public ResponseEntity<CommonResponse> requestPushNotificationToWeb(@PathVariable int page) {
        List<ResponseCrawling.UnivNotice> notices = crawlingService.getUnivNotice(page).orElseGet(() -> null);

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("성공")
                .list(notices)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
