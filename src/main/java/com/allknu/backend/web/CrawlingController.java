package com.allknu.backend.web;


import com.allknu.backend.core.types.MajorNoticeType;
import com.allknu.backend.core.types.UnivNoticeType;
import com.allknu.backend.provider.service.CrawlingService;
import com.allknu.backend.provider.service.KnuMobileApiService;
import com.allknu.backend.web.dto.CommonResponse;
import com.allknu.backend.web.dto.ResponseCrawling;
import com.allknu.backend.web.dto.ResponseKnu;
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

    @GetMapping(value = {"/crawling/notice/univ/{type}/{page}", "/crawling/notice/univ/{type}"}) // 학교 공지사항 크롤링 요청
    public ResponseEntity<CommonResponse> getUnivNotice(@PathVariable String type, @PathVariable(required = false) Optional<Integer> page) {

        UnivNoticeType realType = null;
        try {
            realType = UnivNoticeType.valueOf(type);
        } catch (IllegalArgumentException e) {
            realType = UnivNoticeType.ALL;
        }

        List<ResponseCrawling.UnivNotice> notices = crawlingService.getUnivNotice(page.orElseGet(()->1), realType).orElseGet(() -> null);

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("성공")
                .list(notices)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/crawling/notice/major")
    public ResponseEntity<CommonResponse> getMajorNotice(@RequestParam(required = false, defaultValue = "1") int page, @RequestParam(value = "type", required = true, defaultValue = "SOFTWARE") MajorNoticeType type) {
        //기본 강남대 템플릿을 사용하는 학과 공지사항을 크롤링해 반환한다. 만약 기본 템플릿을 사용하지 않는 학과가 생긴다면 switch로 학과에 따라 분기해 긁어오는 코드를 작성할 것
        List<ResponseCrawling.UnivNotice> notices = crawlingService.getMajorDefaultTemplateNotice(page, type).orElseGet(()->null);

        Map<String, Object> map = new HashMap<>();
        map.put("korean", type.getKorean());
        map.put("notices", notices);

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("성공")
                .list(map)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/crawling/staff")
    public ResponseEntity<CommonResponse> getStaffList() {
        List<ResponseKnu.Staff> list = knuMobileApiService.getKnuStaffInfo().orElseGet(()->List.of());

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("성공")
                .list(list)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
