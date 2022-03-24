package com.allknu.backend.provider.service;

import com.allknu.backend.core.service.KnuVeriusApiServiceInterface;
import com.allknu.backend.exception.errors.KnuApiCallFailedException;
import com.allknu.backend.exception.errors.LoginFailedException;
import com.allknu.backend.web.dto.ResponseKnu;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
//참인재시스템 verius 오타 아님 ㅎㅎ
public class KnuVeriusApiService implements KnuVeriusApiServiceInterface {
    public Optional<Map<String, String>> veriusLogin(Map<String, String> ssoCookies) {
        //sso쿠키로 참인재 로그인
        String url = "https://verius.kangnam.ac.kr/sso.do";
        Map<String, String> veriusCookies = null;
        try {
            Connection.Response res = Jsoup.connect(url)
                    .method(Connection.Method.GET)
                    .cookies(ssoCookies)
                    .ignoreContentType(true)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.61 Safari/537.36")
                    .execute();
            veriusCookies = res.cookies();

        }catch (IOException e) {
            e.printStackTrace();
            throw new KnuApiCallFailedException();
        }
        return Optional.ofNullable(veriusCookies);
    }
    @Override
    public Optional<Map<String, String>> getStudentInfo(Map<String, String> veriusCookies) {
        //참인재시스템에서 학과, 학번, 이름 등 학생 정보를 긁어다 준다.
        //해당 참인재 쿠키로 정보를 긁어온다.
        String url = "https://verius.kangnam.ac.kr/user/Std/MyHm010.do?CURRENT_MENU_CODE=MENU0264&TOP_MENU_CODE=MENU0010";
        Map<String, String> result;
        try {
            Document res = Jsoup.connect(url)
                    .method(Connection.Method.GET)
                    .cookies(veriusCookies)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.61 Safari/537.36")
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

    @Override
    public Optional<List<ResponseKnu.VeriusSatisfaction>> getMyVeriusSatisfactionInfo(Map<String, String> veriusCookies, Integer page) {
        if(page <= 0) page = 1;

        //해당 참인재 쿠키로 정보를 긁어온다.
        String url = "https://verius.kangnam.ac.kr/user/Ep/Ms/EpMs040L.do?CURRENT_MENU_CODE=MENU0052&TOP_MENU_CODE=MENU0010&CURR_PAGE=" + page;
        List<ResponseKnu.VeriusSatisfaction> list = new ArrayList<>();

        try {
            Document res = Jsoup.connect(url)
                    .method(Connection.Method.GET)
                    .cookies(veriusCookies)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.61 Safari/537.36")
                    .get();

            Elements views = res.select("div.bbsListLot tbody"); // bbsListLot tbody를 리스트로 가져온다.

            Iterator<Element> rows = views.select("tr").iterator();
            while(rows.hasNext()) {
                ResponseKnu.VeriusSatisfaction satisfaction;

                Element target = rows.next();
                Elements td = target.select("td"); // td들

                String number = td.get(0).text();
                String name = td.get(1).text();
                String endDate = td.get(2).text();
                String satisfactionEndDate = td.get(3).text();
                String status = td.get(4).text();

                // 만족도조사 링크를 따온다.
                String link = null;
                String linkOnClickText = td.get(4).select("a").attr("onclick");
                int startIdx = linkOnClickText.indexOf("(");
                int endIdx = linkOnClickText.indexOf(")");
                if(endIdx - startIdx > 1) {
                    // 링크가 존재
                    String[] params = linkOnClickText.substring(startIdx + 1, endIdx).split(",");
                    for(int i = 0 ; i < params.length ; i++) {
                        params[i] = params[i].replace("'", "");
                    }
                    link = "https://verius.kangnam.ac.kr/user/Ep/Ms/EpMs040D.do?CURRENT_MENU_CODE=MENU0052&TOP_MENU_CODE=MENU0010" +
                            "&SURVEY_SEQ=" + params[0] + "&SURVEY_GB=" + params[1] + "&INPUT_SEQ=" + params[2];
                }


                // 앞에 strong 태그 내용 삭제, 더 효율적인 방법이 있능가
                name = name.substring(name.indexOf(" "));
                endDate = endDate.substring(endDate.indexOf(" "));
                satisfactionEndDate = satisfactionEndDate.substring(satisfactionEndDate.lastIndexOf(" "));
                status = status.substring(status.indexOf(" "));

                satisfaction = ResponseKnu.VeriusSatisfaction.builder()
                        .number(number)
                        .name(name)
                        .operationEndDate(endDate)
                        .satisfactionEndDate(satisfactionEndDate)
                        .status(status)
                        .link(link)
                        .build();
                list.add(satisfaction);
            }


        } catch (IOException e) {
            System.out.println(e);
            throw new KnuApiCallFailedException();
        }
        return Optional.ofNullable(list);
    }
}
