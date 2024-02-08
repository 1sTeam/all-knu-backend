package com.allknu.backend.knuapi.presentation;


import com.allknu.backend.knuapi.application.CrawlingService;
import com.allknu.backend.knuapi.application.KnuMobileApiService;
import com.allknu.backend.knuapi.application.dto.*;
import com.allknu.backend.knuapi.domain.EventNoticeType;
import com.allknu.backend.knuapi.domain.MajorNoticeType;
import com.allknu.backend.knuapi.domain.UnivNoticeType;
import com.allknu.backend.global.dto.CommonResponse;
import com.allknu.backend.knuapi.domain.scraper.dto.UnivNoticeScraperResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CrawlingController {
    private final CrawlingService crawlingService;
    private final KnuMobileApiService knuMobileApiService;



    @GetMapping(value = {"/crawling/notice/univ/{type}/{page}", "/crawling/notice/univ/{type}"})
    public ResponseEntity<CommonResponse> getUnivNotice(@PathVariable String type, @PathVariable(required = false) Optional<Integer> page) {

        UnivNoticeType realType = null;
        try {
            realType = UnivNoticeType.valueOf(type);
        } catch (IllegalArgumentException e) {
            realType = UnivNoticeType.ALL;
        }

        UnivNoticeResponseDto univNotice = crawlingService.getUnivNotice(page.orElseGet(()->1), realType);

        return new ResponseEntity<>(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("성공")
                .list(univNotice.getUnivNoticeList())
                .build(), HttpStatus.OK);
    }

    @GetMapping(value = {"/crawling/notice/event/{type}/{page}", "/crawling/notice/event/{type}"})
    public ResponseEntity<CommonResponse> getEventNotice(@PathVariable String type, @PathVariable(required = false) Optional<Integer> page) {
        // 학교 공지사항 크롤링
        EventNoticeType realType = null;
        try {
            realType = EventNoticeType.valueOf(type);
        } catch (IllegalArgumentException e) {
            realType = EventNoticeType.ALL;
        }
        EventNoticeResponseDto eventNotice = crawlingService.getEventNotice(page.orElseGet(() -> 1), realType);

        return new ResponseEntity<>(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("성공")
                .list(eventNotice.getEventNoticeList())
                .build(), HttpStatus.OK);
    }

    @GetMapping("/crawling/notice/major")
    public ResponseEntity<CommonResponse> getMajorNotice(@RequestParam(required = false, defaultValue = "1") int page, @RequestParam(value = "type", required = true, defaultValue = "SOFTWARE") MajorNoticeType type) {
        //기본 강남대 템플릿을 사용하는 학과 공지사항을 크롤링해 반환한다. 만약 기본 템플릿을 사용하지 않는 학과가 생긴다면 switch로 학과에 따라 분기해 긁어오는 코드를 작성할 것
        List<ResponseCrawling.UnivNotice> notices = crawlingService.getMajorDefaultTemplateNotice(page, type).orElseGet(()->null);

        Map<String, Object> map = new HashMap<>();
        map.put("korean", type.getKorean());
        map.put("notices", notices);

        return new ResponseEntity<>(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("성공")
                .list(map)
                .build(), HttpStatus.OK);
    }
    @GetMapping("/crawling/staff")
    public ResponseEntity<CommonResponse> getStaffList() {
        List<ResponseKnu.Staff> list = knuMobileApiService.getKnuStaffInfo().orElseGet(()->List.of());

        return new ResponseEntity<>(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("성공")
                .list(list)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/crawling/calendar")
    public ResponseEntity<CommonResponse> getKnuCalendar(){
        CalendarResponseDto response = crawlingService.getKnuCalendar();

        return  new ResponseEntity<>(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("성공")
                .list(response.getCalendarMap())
                .build(),HttpStatus.OK);
    }

}
