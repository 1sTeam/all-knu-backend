package com.allknu.backend.provider.service;

import com.allknu.backend.core.service.KnuVeriusApiServiceInterface;
import com.allknu.backend.exception.errors.KnuApiCallFailedException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
//참인재시스템 verius 오타 아님 ㅎㅎ
public class KnuVeriusApiService implements KnuVeriusApiServiceInterface {
    @Override
    public Optional<Map<String, String>> getStudentInfo(Map<String, String> ssoCookies) {
        //참인재시스템에서 학과, 학번, 이름 등 학생 정보를 긁어다 준다.
        String url = "***REMOVED***?CURRENT_MENU_CODE=MENU0264&TOP_MENU_CODE=MENU0010";
        Map<String, String> result;
        try {
            Document res = Jsoup.connect(url)
                    .method(Connection.Method.GET)
                    .cookies(ssoCookies)
                    .userAgent("***REMOVED***")
                    .get();

            Elements tableViews = res.select("div.tableView tbody"); // tableView tbody를 리스트로 가져온다.
            Elements dataList = tableViews.get(0).select("td"); // 학생 기본정보 테이블의 td들

            result = new HashMap<>();
            result.put("name", dataList.get(0).text());
            result.put("id", dataList.get(1).text());
            result.put("major", dataList.get(3).text());

        } catch (IOException e) {
            System.out.println(e);
            throw new KnuApiCallFailedException();
        }
        return Optional.ofNullable(result);
    }
}
