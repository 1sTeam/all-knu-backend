package com.allknu.backend.provider.service;

import com.allknu.backend.core.service.KnuApiServiceInterface;
import com.allknu.backend.exception.errors.LoginFailedException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
}
