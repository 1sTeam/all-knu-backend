package com.allknu.backend.provider.service;

import com.allknu.backend.core.service.KnuMobileApiServiceInterface;
import com.allknu.backend.exception.errors.LoginFailedException;
import com.allknu.backend.web.dto.ResponseKnu;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class KnuMobileApiService implements KnuMobileApiServiceInterface {

    @Override
    public Optional<JsonNode> getKnuApiJsonData(String url, Map<String, String> cookies) {
        JsonNode result = null;
        //knu api 호출해서 바디만 반환하는 메서드, 실패시 널 반환
        try {
            Connection.Response res = Jsoup.connect(url)
                    .method(Connection.Method.GET)
                    .ignoreContentType(true)
                    .cookies(cookies) // 로그인 쿠키 삽입
                    .userAgent("***REMOVED***")
                    .execute();

            System.out.println(res.body());// 결과

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(res.body()); // json mapper

            if(jsonNode.get("result").toString().equals("\"success\"")) {
                //성공
                result = jsonNode;
            } else {
                //로그인 쿠키가 맞지 않음
                System.out.println("api 조회 실패");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<Map<String, String>> login(String id, String password) {
        String url = "***REMOVED***?user_id=" + id + "&user_pwd=" + password;
        Map<String, String> cookies = null;
        try {
            Connection.Response res = Jsoup.connect(url)
                    .method(Connection.Method.GET)
                    .ignoreContentType(true)
                    .userAgent("***REMOVED***")
                    .execute();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(res.body()); // json mapper

            if(jsonNode.get("result").toString().equals("\"success\"")) {
                System.out.println("로그인 성공");
                cookies = res.cookies();

                for( Map.Entry<String, String> elem : cookies.entrySet() ){
                    System.out.println( String.format("키 : %s, 값 : %s", elem.getKey(), elem.getValue()) );
                }
            } else {
                System.out.println("로그인 실패");
            }

        } catch (IOException e) {
            System.out.println(e);
            throw new LoginFailedException();
        }
        return Optional.ofNullable(cookies);
    }

    @Override
    public void logout(Map<String, String> cookies) {
        String url = "***REMOVED***";
        try {
            Connection.Response res = Jsoup.connect(url)
                    .method(Connection.Method.GET)
                    .ignoreContentType(true)
                    .cookies(cookies)
                    .userAgent("***REMOVED***")
                    .execute();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public Optional<ResponseKnu.TimeTable> getTimeTable(Map<String, String> cookies) {
        ResponseKnu.TimeTable timeTable = null;

        String url = "***REMOVED***";
        try {
            Connection.Response res = Jsoup.connect(url)
                    .method(Connection.Method.GET)
                    .ignoreContentType(true)
                    .cookies(cookies) // 로그인 쿠키 삽입
                    .userAgent("***REMOVED***")
                    .execute();

            System.out.println(res.body());// 결과

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(res.body()); // json mapper
            List<Object> timeTableList = new ArrayList<>();

            if(jsonNode.get("result").toString().equals("\"success\"")) {
                //시간표 가져오기 성공
                if (jsonNode.get("data").isArray()) {
                    for (final JsonNode objNode : jsonNode.get("data")) {
                        //시간표 항목들
                        System.out.println(objNode);
                        timeTableList.add(objNode);
                    }
                }
                timeTable = ResponseKnu.TimeTable.builder()
                        .data(timeTableList)
                        .build();
            } else {
                //로그인 쿠키가 맞지 않음
                System.out.println("시간표 조회 실패");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(timeTable);
    }

    @Override
    public Optional<ResponseKnu.PeriodUniv> getPeriodOfUniv(Map<String, String> cookies) {
        //학교 재학 년도, 학기 구하기
        ResponseKnu.PeriodUniv responsePeriod = null;

        JsonNode knuData = getKnuApiJsonData("***REMOVED***", cookies).orElseGet(()->null);
        if(knuData != null) {
            //success
            List<Object> periodList = new ArrayList<>();

            if (knuData.get("data").isArray()) {
                for (final JsonNode objNode : knuData.get("data")) {
                    //재학기간 항목들
                    System.out.println(objNode);
                    periodList.add(objNode);
                }
            }
            responsePeriod = ResponseKnu.PeriodUniv.builder()
                    .data(periodList)
                    .build();
        } else {
            //fail
            System.out.println("재학기간 조회 실패");
        }
        return Optional.ofNullable(responsePeriod);
    }

    @Override
    public Optional<ResponseKnu.Grade> getGrade(Map<String, String> cookies, String year, String semester) {
        //년도, 학기 넣어서 성적 구하기
        ResponseKnu.Grade responseGrade = null;

        JsonNode knuData = getKnuApiJsonData("***REMOVED***?schl_year=" + year + "&schl_smst=" + semester, cookies).orElseGet(()->null);
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
                    System.out.println(objNode);
                    detailList.add(objNode);
                }
            }
            result.put("detail", detailList);

            responseGrade = ResponseKnu.Grade.builder()
                    .data(result)
                    .build();
        } else {
            //fail
            System.out.println("재학기간 조회 실패");
        }
        return Optional.ofNullable(responseGrade);
    }
}
