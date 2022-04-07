package com.allknu.backend.web;

import com.allknu.backend.exception.errors.KnuApiCallFailedException;
import com.allknu.backend.exception.errors.LoginFailedException;
import com.allknu.backend.provider.service.KnuApiService;
import com.allknu.backend.provider.service.KnuMobileApiService;
import com.allknu.backend.provider.service.KnuVeriusApiService;
import com.allknu.backend.web.dto.CommonResponse;
import com.allknu.backend.web.dto.RequestKnu;
import com.allknu.backend.web.dto.ResponseKnu;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class KnuApiController {
    private final KnuMobileApiService knuMobileApiService;
    private final KnuApiService knuApiService;
    private final KnuVeriusApiService knuVeriusApiService;

    @PostMapping("/knu/login")
    public ResponseEntity<CommonResponse> knuLogin(@Valid @RequestBody RequestKnu.Login loginDto) {
        //모바일 로그인
        Map<String, String> mobileCookies = knuMobileApiService.login(loginDto.getId(), loginDto.getPassword()).orElseThrow(()->new LoginFailedException());
        //통합 SSO 로그인
        Map<String, String> ssoCookies = knuApiService.ssoLogin(loginDto.getId(), loginDto.getPassword()).orElseThrow(()->new LoginFailedException());
        // 참인재 로그인
        Map<String, String> veriusCookies = knuVeriusApiService.veriusLogin(ssoCookies).orElseThrow(()->new LoginFailedException());
        //학생 정보 긁어오기
        Map<String, String> studentInfo = knuVeriusApiService.getStudentInfo(veriusCookies).orElseThrow(()->new KnuApiCallFailedException());

        // 세션인포 정보 삽입
        Map<String, Object> sessionInfo = new HashMap<>();
        sessionInfo.put("mobileCookies", mobileCookies);
        sessionInfo.put("ssoCookies", ssoCookies);
        sessionInfo.put("veriusCookies", veriusCookies);

        Map<String, Object> responseList = new HashMap<>();
        responseList.put("sessionInfo", sessionInfo);
        responseList.put("studentInfo", studentInfo);

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("로그인 성공")
                .list(responseList)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/knu/login/staff")
    public ResponseEntity<CommonResponse> knuStaffLogin(@Valid @RequestBody RequestKnu.Login loginDto) {
        //통합 SSO 로그인
        Map<String, String> ssoCookies = knuApiService.ssoLogin(loginDto.getId(), loginDto.getPassword()).orElseThrow(()->new LoginFailedException());
        // 참인재 로그인
        Map<String, String> veriusCookies = knuVeriusApiService.veriusLogin(ssoCookies).orElseThrow(()->new LoginFailedException());

        // 세션인포 정보 삽입
        Map<String, Object> sessionInfo = new HashMap<>();
        sessionInfo.put("ssoCookies", ssoCookies);
        sessionInfo.put("veriusCookies", veriusCookies);

        Map<String, Object> responseList = new HashMap<>();
        responseList.put("sessionInfo", sessionInfo);

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("로그인 성공")
                .list(responseList)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/knu/refresh/session")
    public ResponseEntity<CommonResponse> knuRefreshSession(@Valid @RequestBody RequestKnu.Refresh refreshDto) {
        //모바일은 유효여부 판단 후 사용하고 나머지는 새로 만든다
        //모바일 로그인
        Map<String, String> mobileCookies = knuMobileApiService.refreshSession(refreshDto.getId(), refreshDto.getPassword(),refreshDto.getSessionInfo()).orElseThrow(()->new LoginFailedException());
        //통합 SSO 로그인
        Map<String, String> ssoCookies = knuApiService.ssoLogin(refreshDto.getId(), refreshDto.getPassword()).orElseThrow(()->new LoginFailedException());
        // 참인재 로그인
        Map<String, String> veriusCookies = knuVeriusApiService.veriusLogin(ssoCookies).orElseThrow(()->new LoginFailedException());


        // 세션인포 정보 삽입
        Map<String, Object> sessionInfo = new HashMap<>();
        sessionInfo.put("mobileCookies", mobileCookies);
        sessionInfo.put("ssoCookies", ssoCookies);
        sessionInfo.put("veriusCookies", veriusCookies);

        Map<String, Object> responseList = new HashMap<>();
        responseList.put("sessionInfo", sessionInfo);

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("로그인 성공")
                .list(responseList)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/knu/logout")
    public ResponseEntity<CommonResponse> knuLogout(@RequestBody RequestKnu.Logout logoutDto) {

        knuMobileApiService.logout(logoutDto.getSessionInfo().getMobileCookies());

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("로그아웃 성공")
                .list(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/knu/timetable")
    public ResponseEntity<CommonResponse> knuTimeTable(@RequestBody RequestKnu.Timetable timetableDto) {
        System.out.println(timetableDto.getSessionInfo().getMobileCookies());
        ResponseKnu.TimeTable responseTimeTable
                = knuMobileApiService.getTimeTable(timetableDto.getSessionInfo().getMobileCookies()).orElseThrow(()->new KnuApiCallFailedException());

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("시간표 성공")
                .list(responseTimeTable)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/knu/period")
    public ResponseEntity<CommonResponse> getPeriodUniv(@RequestBody RequestKnu.Period periodDto) {

        ResponseKnu.PeriodUniv period
                = knuMobileApiService.getPeriodOfUniv(periodDto.getSessionInfo().getMobileCookies()).orElseThrow(()->new KnuApiCallFailedException());

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("재학 기간 조회 성공")
                .list(period)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/knu/grade")
    public ResponseEntity<CommonResponse> getKnuGrade(@Valid @RequestBody RequestKnu.Grade requestGradeDto) {

        ResponseKnu.Grade grade = knuMobileApiService.getGrade(requestGradeDto.getSessionInfo().getMobileCookies(), requestGradeDto.getYear(), requestGradeDto.getSemester())
                .orElseThrow(()->new KnuApiCallFailedException());

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("성적 조회 성공")
                .list(grade)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/knu/calendar")
    public ResponseEntity<CommonResponse> getKnuCalendar() {
        List<ResponseKnu.CalendarItem> itemList = knuMobileApiService.getKnuCalendar().orElseGet(()->List.of());
        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("조회 성공")
                .list(itemList)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/knu/scholarship")
    public ResponseEntity<CommonResponse> getKnuScholarship(@RequestBody RequestKnu.Scholarship scholarship) {

        List<ResponseKnu.ScholarshipItem> itemList
                = knuMobileApiService.getMyScholarship(scholarship.getSessionInfo().getMobileCookies()).orElseThrow(()->new KnuApiCallFailedException());

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("조회 성공")
                .list(itemList)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/knu/tuition")
    public ResponseEntity<CommonResponse> getKnuTuition(@RequestBody RequestKnu.Tuition tuition) {

        ResponseKnu.Tuition result = knuMobileApiService.getMyTuition(tuition.getSessionInfo().getMobileCookies(), tuition.getYear(), tuition.getSemester())
                .orElseThrow(()->new KnuApiCallFailedException());

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("조회 성공")
                .list(result)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/knu/verius/satisfaction")
    public ResponseEntity<CommonResponse> getKnuVeriusSatisfaction(@RequestBody RequestKnu.VeriusSatisfaction veriusSatisfaction) {

        List<ResponseKnu.VeriusSatisfaction> list
                = knuVeriusApiService.getMyVeriusSatisfactionInfo(veriusSatisfaction.getSessionInfo().getVeriusCookies(), veriusSatisfaction.getPage()).orElseThrow(()->new KnuApiCallFailedException());

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("조회 성공")
                .list(list)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/knu/verius/program/participate")
    public ResponseEntity<CommonResponse> getMyVeriusProgram(@RequestBody RequestKnu.MyVeriusProgram myVeriusProgram){
        List<ResponseKnu.MyVeriusProgram> list
                = knuVeriusApiService.getMyVeriusProgram(myVeriusProgram.getSessionInfo().getVeriusCookies(), myVeriusProgram.getPage()).orElseThrow(()->new KnuApiCallFailedException());

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("조회 성공")
                .list(list)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
