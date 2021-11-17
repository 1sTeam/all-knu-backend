package com.allknu.backend.web;

import com.allknu.backend.exception.errors.LoginFailedException;
import com.allknu.backend.provider.service.KnuMobileApiService;
import com.allknu.backend.web.dto.CommonResponse;
import com.allknu.backend.web.dto.RequestKnu;
import com.allknu.backend.web.dto.ResponseKnu;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class KnuApiController {
    private final KnuMobileApiService knuMobileApiService;

    @PostMapping("/knu/login")
    public ResponseEntity<CommonResponse> knuLogin(@Valid @RequestBody RequestKnu.Login loginDto) {

        Map<String, String> cookies = knuMobileApiService.login(loginDto.getId(), loginDto.getPassword()).orElseThrow(()->new LoginFailedException());

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("로그인 성공")
                .list(cookies)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/knu/logout")
    public ResponseEntity<CommonResponse> knuLogout(@RequestBody Map<String, String> cookies) {

        knuMobileApiService.logout(cookies);

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("로그아웃 성공")
                .list(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/knu/timetable")
    public ResponseEntity<CommonResponse> knuTimeTable(@RequestBody Map<String, String> cookies) {

        ResponseKnu.TimeTable responseTimeTable = knuMobileApiService.getTimeTable(cookies).orElseThrow(()->new LoginFailedException());

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("시간표 성공")
                .list(responseTimeTable)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/knu/period")
    public ResponseEntity<CommonResponse> getPeriodUniv(@RequestBody Map<String, String> cookies) {

        ResponseKnu.PeriodUniv period = knuMobileApiService.getPeriodOfUniv(cookies).orElseThrow(()->new LoginFailedException());

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("재학 기간 조회 성공")
                .list(period)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/knu/grade")
    public ResponseEntity<CommonResponse> getKnuGrade(@Valid @RequestBody RequestKnu.Grade requestGradeDto) {

        ResponseKnu.Grade grade = knuMobileApiService.getGrade(requestGradeDto.getCookies(), requestGradeDto.getYear(), requestGradeDto.getSemester())
                .orElseThrow(()->new LoginFailedException());

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("성적 조회 성공")
                .list(grade)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
