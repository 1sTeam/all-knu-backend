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
    private Optional<Map<String, String>> veriusLogin(Map<String, String> ssoCookies) {
        //sso쿠키로 참인재 로그인
        String url = "***REMOVED***";
        Map<String, String> veriusCookies = null;
        try {
            Connection.Response res = Jsoup.connect(url)
                    .method(Connection.Method.GET)
                    .cookies(ssoCookies)
                    .ignoreContentType(true)
                    .userAgent("***REMOVED***")
                    .execute();
            veriusCookies = res.cookies();

        }catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(veriusCookies);
    }
    @Override
    public Optional<Map<String, String>> getStudentInfo(Map<String, String> ssoCookies) {
        //참인재시스템에서 학과, 학번, 이름 등 학생 정보를 긁어다 준다.
        //먼저 sso쿠키로 참인재에 로그인한다.
        Map<String, String> veriusCookies = veriusLogin(ssoCookies).orElseThrow(()->new KnuApiCallFailedException());
        //해당 참인재 쿠키로 정보를 긁어온다.
        String url = "***REMOVED***?CURRENT_MENU_CODE=MENU0264&TOP_MENU_CODE=MENU0010";
        Map<String, String> result;
        try {
            Document res = Jsoup.connect(url)
                    .method(Connection.Method.GET)
                    .cookies(veriusCookies)
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

    @Override
    public Optional<List<ResponseKnu.VeriusSatisfaction>> getMyVeriusSatisfactionInfo(Map<String, String> ssoCookies, Integer page) {
        Map<String, String> veriusCookies = veriusLogin(ssoCookies).orElseThrow(()->new LoginFailedException());
        if(page <= 0) page = 1;

        //해당 참인재 쿠키로 정보를 긁어온다.
        String url = "***REMOVED***?CURRENT_MENU_CODE=MENU0052&TOP_MENU_CODE=MENU0010&CURR_PAGE=" + page;
        List<ResponseKnu.VeriusSatisfaction> list = new ArrayList<>();

        try {
            Document res = Jsoup.connect(url)
                    .method(Connection.Method.GET)
                    .cookies(veriusCookies)
                    .userAgent("***REMOVED***")
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
