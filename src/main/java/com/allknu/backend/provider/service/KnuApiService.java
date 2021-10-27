package com.allknu.backend.provider.service;

import com.allknu.backend.core.service.KnuApiServiceInterface;
import com.allknu.backend.exception.errors.LoginFailedException;
import com.allknu.backend.web.dto.RequestKnu;
import com.allknu.backend.web.dto.ResponseKnu;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class KnuApiService implements KnuApiServiceInterface {

    @Override
    public Optional<Map<String, String>> login(String id, String password) {
        String url = "https://m.kangnam.ac.kr/knusmart/c/c001.do?user_id=" + id + "&user_pwd=" + password;
        Map<String, String> cookies = null;
        try {
            Connection.Response res = Jsoup.connect(url)
                    .method(Connection.Method.GET)
                    .ignoreContentType(true)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.61 Safari/537.36")
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
        String url = "https://m.kangnam.ac.kr/knusmart/c/c003.do";
        try {
            Connection.Response res = Jsoup.connect(url)
                    .method(Connection.Method.GET)
                    .ignoreContentType(true)
                    .cookies(cookies)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.61 Safari/537.36")
                    .execute();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public Optional<ResponseKnu.TimeTable> getTimeTable(Map<String, String> cookies) {
        ResponseKnu.TimeTable timeTable = null;

        String url = "https://m.kangnam.ac.kr/knusmart/s/s251.do";
        try {
            Connection.Response res = Jsoup.connect(url)
                    .method(Connection.Method.GET)
                    .ignoreContentType(true)
                    .cookies(cookies) // 로그인 쿠키 삽입
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.61 Safari/537.36")
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
}
