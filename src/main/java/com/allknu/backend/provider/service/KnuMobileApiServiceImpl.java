package com.allknu.backend.provider.service;

import com.allknu.backend.core.service.KnuMobileApiService;
import com.allknu.backend.web.dto.ResponseKnu;
import com.allknu.backend.web.dto.SessionInfo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class KnuMobileApiServiceImpl implements KnuMobileApiService {
    private static final Logger log = LoggerFactory.getLogger(KnuMobileApiServiceImpl.class);

    @Override
    public Optional<JsonNode> getKnuApiJsonData(String url, Map<String, String> cookies) {
        JsonNode result = null;
        //knu api 호출해서 바디만 반환하는 메서드, 실패시 널 반환
        try {
            Connection connection = Jsoup.connect(url)
                    .method(Connection.Method.GET)
                    .ignoreContentType(true)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.61 Safari/537.36");
            if(cookies != null) connection.cookies(cookies);

            Connection.Response res = connection.execute();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(res.body()); // json mapper

            if(jsonNode.get("result").toString().equals("\"success\"")) {
                //성공
                result = jsonNode;
            } else {
                //로그인 쿠키가 맞지 않음
                log.info("로그인 쿠키가 맞지 않아 api 호출 실패");
            }
        } catch (IOException e) {
            log.error("getKnuApiJsonData() error " + e);
        }
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<ResponseKnu.TimeTable> getTimeTable(Map<String, String> cookies) {
        ResponseKnu.TimeTable timeTable = null;

        JsonNode knuData = getKnuApiJsonData("https://m.kangnam.ac.kr/knusmart/s/s251.do", cookies).orElseGet(()->null);
        if(knuData != null) {
            //시간표 가져오기 성공
            List<Object> timeTableList = new ArrayList<>();
            if (knuData.get("data").isArray()) {
                for (final JsonNode objNode : knuData.get("data")) {
                    //시간표 항목들
                    timeTableList.add(objNode);
                }
            }
            timeTable = ResponseKnu.TimeTable.builder()
                    .data(timeTableList)
                    .build();
        }
        else {
            //fail
            log.info("시간표 조회 실패");
        }

        return Optional.ofNullable(timeTable);
    }

    @Override
    public Optional<ResponseKnu.PeriodUniv> getPeriodOfUniv(Map<String, String> cookies) {
        //학교 재학 년도, 학기 구하기
        ResponseKnu.PeriodUniv responsePeriod = null;

        JsonNode knuData = getKnuApiJsonData("https://m.kangnam.ac.kr/knusmart/s/s252l.do", cookies).orElseGet(()->null);
        if(knuData != null) {
            //success
            List<Object> periodList = new ArrayList<>();

            if (knuData.get("data").isArray()) {
                for (final JsonNode objNode : knuData.get("data")) {
                    //재학기간 항목들
                    periodList.add(objNode);
                }
            }
            responsePeriod = ResponseKnu.PeriodUniv.builder()
                    .data(periodList)
                    .build();
        } else {
            //fail
            log.info("재학기간 조회 실패");
        }
        return Optional.ofNullable(responsePeriod);
    }

    @Override
    public Optional<ResponseKnu.Grade> getGrade(Map<String, String> cookies, String year, String semester) {
        //년도, 학기 넣어서 성적 구하기
        ResponseKnu.Grade responseGrade = null;

        JsonNode knuData = getKnuApiJsonData("https://m.kangnam.ac.kr/knusmart/s/s252.do?schl_year=" + year + "&schl_smst=" + semester, cookies).orElseGet(()->null);
        if(knuData != null) {
            //success
            Map<String, Object> result = new HashMap<>();
            List<Object> detailList = new ArrayList<>();

            //total 학점은 total을 키로 json 삽입
            result.put("total", knuData.get("data"));

            //세부 학점 항목들
            if (knuData.get("data2").isArray()) {
                for (final JsonNode objNode : knuData.get("data2")) {
                    //재학기간 항목들
                    detailList.add(objNode);
                }
            }
            result.put("detail", detailList);

            responseGrade = ResponseKnu.Grade.builder()
                    .data(result)
                    .build();
        } else {
            //fail
            log.info("성적조회 실패");
        }
        return Optional.ofNullable(responseGrade);
    }

    @Override
    public Optional<List<ResponseKnu.CalendarItem>> getKnuCalendar() {
        //gubn이 1이면 학생용, 2면 교수용인듯
        JsonNode knuData = getKnuApiJsonData("https://m.kangnam.ac.kr/knusmart/p/p121.do?cors_gubn=1", Map.of()).orElseGet(()->null);

        List<ResponseKnu.CalendarItem> list = null;
        if(knuData != null) {
            //success
            list = new ArrayList<>();
            if (knuData.get("data").isArray()) {
                for (final JsonNode objNode : knuData.get("data")) {
                    //캘린더 항목들
                    String start = objNode.get("used_sdat").toString().substring(
                            objNode.get("used_sdat").toString().indexOf(">") + 1,
                            objNode.get("used_sdat").toString().indexOf('일') + 1
                    ); // 문자열 자르기로 시작날짜 가져오기
                    String end = objNode.get("used_edat").asText();
                    String describe = objNode.get("used_desc").asText();
                    String year = objNode.get("used_yyyy").asText();

                    ResponseKnu.CalendarItem item = ResponseKnu.CalendarItem.builder()
                            .start(start)
                            .end(end)
                            .describe(describe)
                            .year(year)
                            .build();
                    list.add(item);
                }
            }
        } else {
            //fail
            log.info("학사일정 조회 실패");
        }
        return Optional.ofNullable(list);
    }

    @Override
    public Optional<List<ResponseKnu.ScholarshipItem>> getMyScholarship(Map<String, String> cookies) {
        JsonNode knuData = getKnuApiJsonData("https://m.kangnam.ac.kr/knusmart/s/s253.do", cookies).orElseGet(()->null);

        List<ResponseKnu.ScholarshipItem> list = null;
        if(knuData != null) {
            //success
            list = new ArrayList<>();
            if (knuData.get("data").isArray()) {
                for (final JsonNode objNode : knuData.get("data")) {
                    String amount = objNode.get("schp_amnt").asText();
                    String year = objNode.get("schl_year").asText();
                    String department = objNode.get("dept_code").asText();
                    String semester = objNode.get("schl_smst").asText();
                    String grade = objNode.get("stnt_grad").asText();
                    String describe = objNode.get("schp_kfnm").asText();

                    ResponseKnu.ScholarshipItem item = ResponseKnu.ScholarshipItem.builder()
                            .amount(amount)
                            .year(year)
                            .department(department)
                            .semester(semester)
                            .grade(grade)
                            .describe(describe)
                            .build();
                    list.add(item);
                }
            }
        } else {
            //fail
            log.info("장학금조회 실패");
        }
        return Optional.ofNullable(list);
    }

    @Override
    public Optional<ResponseKnu.Tuition> getMyTuition(Map<String, String> cookies, Integer year, Integer semester) {
        JsonNode knuData = getKnuApiJsonData("https://m.kangnam.ac.kr/knusmart/s/s260.do?schl_year=" + year + "&schl_smst=" + semester, cookies).orElseGet(()->null);

        ResponseKnu.Tuition tuition = null;
        if(knuData != null) {
            //success
            if (knuData.get("data").isArray()) {
                for (final JsonNode objNode : knuData.get("data")) {
                    String date = objNode.get("schl_date").asText();
                    String bankName = objNode.get("bank_name").asText();
                    String term = objNode.get("used_term").asText();
                    String amount = objNode.get("act_sum").asText();
                    String bankNumber = objNode.get("bank_numb").asText();
                    String dividedAmount = objNode.get("divd_gubn").asText();
                    String dividedPay = objNode.get("pay_gubn").toString().substring(
                            objNode.get("pay_gubn").toString().indexOf(">") + 1,
                            objNode.get("pay_gubn").toString().lastIndexOf('<')
                    ); // 문자열 자르기로 가져오기

                    tuition = ResponseKnu.Tuition.builder()
                            .dividedAmount(dividedAmount)
                            .term(term)
                            .amount(amount)
                            .bank(bankName)
                            .bankNumber(bankNumber)
                            .date(date)
                            .dividedPay(dividedPay)
                            .build();
                }
            }
        } else {
            //fail
            log.info("등록금 조회 실패");
        }
        return Optional.ofNullable(tuition);
    }

    @Override
    public Optional<List<ResponseKnu.Staff>> getKnuStaffInfo() {
        JsonNode knuData = getKnuApiJsonData("https://m.kangnam.ac.kr/knusmart/p/p118.do", Map.of()).orElseGet(()->null);

        List<ResponseKnu.Staff> list = new ArrayList<>();
        if(knuData != null) {
            //success
            if (knuData.get("data").isArray()) {
                for (final JsonNode objNode : knuData.get("data")) {
                    String workOn = objNode.get("work_on").asText();
                    String userName = objNode.get("user_name").asText();
                    String mail = objNode.get("mail_addr").asText();
                    String office = objNode.get("tele_offi").asText();
                    String department = objNode.get("dept_name").asText();
                    String location = objNode.get("user_loca").asText();

                    ResponseKnu.Staff staff = ResponseKnu.Staff.builder()
                            .userName(userName)
                            .workOn(workOn)
                            .mail(mail)
                            .office(office)
                            .department(department)
                            .location(location)
                            .build();

                    list.add(staff);
                }
            }
        } else {
            //fail
            log.error("교직원 조회 실패");
        }
        return Optional.ofNullable(list);
    }
}
