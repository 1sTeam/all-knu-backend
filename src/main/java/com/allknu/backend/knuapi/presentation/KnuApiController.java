package com.allknu.backend.knuapi.presentation;

import com.allknu.backend.knuapi.application.KnuMobileApiService;
import com.allknu.backend.knuapi.application.KnuVeriusApiService;
import com.allknu.backend.global.exception.errors.KnuApiCallFailedException;
import com.allknu.backend.global.dto.CommonResponse;
import com.allknu.backend.knuapi.application.dto.KnuVeriusSatisfactionSurveyResponseDto;
import com.allknu.backend.knuapi.application.dto.RequestKnu;
import com.allknu.backend.knuapi.application.dto.ResponseKnu;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class KnuApiController {
    private final KnuMobileApiService knuMobileApiService;
    private final KnuVeriusApiService knuVeriusApiService;

    @PostMapping("/knu/timetable")
    public ResponseEntity<CommonResponse> knuTimeTable(@RequestBody RequestKnu.Timetable timetableDto) {
        ResponseKnu.TimeTable responseTimeTable
                = knuMobileApiService.getTimeTable(timetableDto.getSessionInfo().getMobileCookies()).orElseThrow(()->new KnuApiCallFailedException());

        return new ResponseEntity<>(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("시간표 성공")
                .list(responseTimeTable)
                .build(), HttpStatus.OK);
    }
    @PostMapping("/knu/period")
    public ResponseEntity<CommonResponse> getPeriodUniv(@RequestBody RequestKnu.Period periodDto) {

        ResponseKnu.PeriodUniv period
                = knuMobileApiService.getPeriodOfUniv(periodDto.getSessionInfo().getMobileCookies()).orElseThrow(()->new KnuApiCallFailedException());

        return new ResponseEntity<>(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("재학 기간 조회 성공")
                .list(period)
                .build(), HttpStatus.OK);
    }

    @PostMapping("/knu/grade")
    public ResponseEntity<CommonResponse> getKnuGrade(@Valid @RequestBody RequestKnu.Grade requestGradeDto) {

        ResponseKnu.Grade grade = knuMobileApiService.getGrade(requestGradeDto.getSessionInfo().getMobileCookies(), requestGradeDto.getYear(), requestGradeDto.getSemester())
                .orElseThrow(()->new KnuApiCallFailedException());

        return new ResponseEntity<>(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("성적 조회 성공")
                .list(grade)
                .build(), HttpStatus.OK);
    }
    @GetMapping("/knu/calendar")
    public ResponseEntity<CommonResponse> getKnuCalendar() {
        List<ResponseKnu.CalendarItem> itemList = knuMobileApiService.getKnuCalendar().orElseGet(()->List.of());
        return new ResponseEntity<>(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("조회 성공")
                .list(itemList)
                .build(), HttpStatus.OK);
    }
    @PostMapping("/knu/scholarship")
    public ResponseEntity<CommonResponse> getKnuScholarship(@RequestBody RequestKnu.Scholarship scholarship) {

        List<ResponseKnu.ScholarshipItem> itemList
                = knuMobileApiService.getMyScholarship(scholarship.getSessionInfo().getMobileCookies()).orElseThrow(()->new KnuApiCallFailedException());

        return new ResponseEntity<>(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("조회 성공")
                .list(itemList)
                .build(), HttpStatus.OK);
    }
    @PostMapping("/knu/tuition")
    public ResponseEntity<CommonResponse> getKnuTuition(@RequestBody RequestKnu.Tuition tuition) {

        ResponseKnu.Tuition result = knuMobileApiService.getMyTuition(tuition.getSessionInfo().getMobileCookies(), tuition.getYear(), tuition.getSemester())
                .orElseThrow(()->new KnuApiCallFailedException());

        return new ResponseEntity<>(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("조회 성공")
                .list(result)
                .build(), HttpStatus.OK);
    }
    @PostMapping("/knu/verius/satisfaction")
    public ResponseEntity<CommonResponse> getKnuVeriusSatisfaction(@RequestBody RequestKnu.VeriusSatisfaction veriusSatisfaction) {

        KnuVeriusSatisfactionSurveyResponseDto responseDto = knuVeriusApiService.getMyVeriusSatisfactionInfo(
                veriusSatisfaction.getSessionInfo().getVeriusCookies(), veriusSatisfaction.getPage());

        return new ResponseEntity<>(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("조회 성공")
                .list(responseDto.getItems())
                .build(), HttpStatus.OK);
    }
    @PostMapping("/knu/verius/program/participate")
    public ResponseEntity<CommonResponse> getMyVeriusProgram(@RequestBody RequestKnu.MyVeriusProgram myVeriusProgram){
        List<ResponseKnu.MyVeriusProgram> list
                = knuVeriusApiService.getMyVeriusProgram(myVeriusProgram.getSessionInfo().getVeriusCookies(), myVeriusProgram.getPage()).orElseThrow(()->new KnuApiCallFailedException());

        return new ResponseEntity<>(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("조회 성공")
                .list(list)
                .build(), HttpStatus.OK);
    }
    @PostMapping("/knu/verius/mileage")
    public ResponseEntity<CommonResponse>getVeriusMileage(@RequestBody RequestKnu.Mileage mileage){
        Map<String,Map<String,Integer>> list = knuVeriusApiService.getMileage(mileage.getSessionInfo().getVeriusCookies()).orElseThrow(() -> new KnuApiCallFailedException());

        return new ResponseEntity<>(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("마일리지 조회 성공")
                .list(list)
                .build(), HttpStatus.OK);
    }
}
