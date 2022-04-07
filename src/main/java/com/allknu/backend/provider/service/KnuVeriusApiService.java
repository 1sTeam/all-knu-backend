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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
//참인재시스템 verius 오타 아님 ㅎㅎ
public class KnuVeriusApiService implements KnuVeriusApiServiceInterface {
    public Optional<Map<String, String>> veriusLogin(Map<String, String> ssoCookies) {
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
            throw new KnuApiCallFailedException();
        }
        return Optional.ofNullable(veriusCookies);
    }
    @Override
    public Optional<Map<String, String>> getStudentInfo(Map<String, String> veriusCookies) {
        //참인재시스템에서 학과, 학번, 이름 등 학생 정보를 긁어다 준다.
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
    public Optional<List<ResponseKnu.VeriusSatisfaction>> getMyVeriusSatisfactionInfo(Map<String, String> veriusCookies, Integer page) {
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
                    link = "***REMOVED***?CURRENT_MENU_CODE=MENU0052&TOP_MENU_CODE=MENU0010" +
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

    @Override
    public Optional<List<ResponseKnu.MyVeriusProgram>> getMyVeriusProgram(Map<String, String> veriusCookies, int page) {
        List<ResponseKnu.MyVeriusProgram> lists = new ArrayList<>();

        String url = "***REMOVED***?CURRENT_MENU_CODE=MENU0049&TOP_MENU_CODE=MENU0010&CURR_PAGE="+page;
        //크롤링하기
        try{
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
                    .cookies(veriusCookies)
                    .get();

            Iterator<Element> rows = doc.select("#frm > div > div > table > tbody > tr").iterator();
            while(rows.hasNext()) {
                Element target = rows.next();
                Elements td = target.select("td"); // tr의 td들
                Elements num= target.select("a");// title
                String title = td.get(2).text();     //제목
                String[] number = num.get(0).attr("href").split("'");
                String applicationDate1 = td.get(9).text();     //신청일
                String[] operationPeriod = td.get(4).text().split("~");     //운영기간
                String department = td.get(5).text();
                String link = null;         //제목 링크
                if("P" == number[3]){
                    link = "***REMOVED***?CURRENT_MENU_CODE=MENU0049&TOP_MENU_CODE=MENU0010&PRM_SEQ="+number[1];
                }else{
                    link = "***REMOVED***?CURRENT_MENU_CODE=MENU0049&TOP_MENU_CODE=MENU0010&PRM_SEQ="+number[1];
                }

                SimpleDateFormat operationFormatter = new SimpleDateFormat("yyyy.MM.dd HH:mm");
                SimpleDateFormat applicationFormatter = new SimpleDateFormat("yyyy.MM.dd");

                Date applicationDate = applicationFormatter.parse(applicationDate1);    //신청일
                Date operationPeriodStart = operationFormatter.parse(operationPeriod[0]);    //운영시작
                Date operationPeriodEnd = operationFormatter.parse(operationPeriod[1]);  //운영종료

                ResponseKnu.MyVeriusProgram myVeriusProgram = ResponseKnu.MyVeriusProgram.builder()
                        .department(department)
                        .link(link)
                        .number(number[1])
                        .applicationDate(applicationDate)
                        .operationPeriodStart(operationPeriodStart)
                        .operationPeriodEnd(operationPeriodEnd)
                        .title(title)
                        .build();
                lists.add(myVeriusProgram);
                System.out.println(lists);
            }
        } catch (IOException | ParseException e){
            System.out.println(e);

        }
        return Optional.ofNullable(lists);
    }
}
