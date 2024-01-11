package com.allknu.backend.knuapi.application;

import com.allknu.backend.global.asset.ApiEndpointSecretProperties;
import com.allknu.backend.global.crawling.Scraper;
import com.allknu.backend.knuapi.application.dto.KnuVeriusSatisfactionSurveyResponseDto;
import com.allknu.backend.knuapi.domain.MajorNoticeType;
import com.allknu.backend.global.exception.errors.KnuApiCallFailedException;
import com.allknu.backend.knuapi.application.dto.ResponseKnu;
import com.allknu.backend.knuapi.domain.scraper.KnuVeriusSatisfactionSurveyScraper;
import com.allknu.backend.knuapi.domain.scraper.dto.KnuVeriusSatisfactionSurveyScraperResponseDto;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class KnuVeriusApiServiceImpl implements KnuVeriusApiService {
    private static final Logger logger = LoggerFactory.getLogger(KnuVeriusApiServiceImpl.class);
    private final ApiEndpointSecretProperties apiEndpointSecretProperties;

    @Override
    public Optional<Map<String, String>> getStudentInfo(Map<String, String> veriusCookies) {
        //참인재시스템에서 학과, 학번, 이름 등 학생 정보를 긁어다 준다.
        //해당 참인재 쿠키로 정보를 긁어온다.
        String url = apiEndpointSecretProperties.getCrawling().getVeriusStudentInfo() + "?CURRENT_MENU_CODE=MENU0028&TOP_MENU_CODE=MENU0017";
        Map<String, String> result = null;
        try {
            Document res = Jsoup.connect(url)
                    .method(Connection.Method.GET)
                    .cookies(veriusCookies)
                    .userAgent(apiEndpointSecretProperties.getCrawling().getUserAgent())
                    .get();

            Elements tableViews = res.select("div.tableView tbody"); // tableView tbody를 리스트로 가져온다.
            Elements dataList = tableViews.get(0).select("td"); // 학생 기본정보 테이블의 td들

            result = new HashMap<>();
            result.put("name", dataList.get(0).text());
            result.put("id", dataList.get(1).text());
            result.put("major", dataList.get(3).text()); //전공
            result.put("topic", MajorNoticeType.findByMajor(dataList.get(3).text()).toString());
        } catch (IOException e) {
            logger.error("getStudentInfo IOException error " + e);
            throw new KnuApiCallFailedException();
        } catch (IndexOutOfBoundsException e) {
            logger.error("세션이 유효하지않아 학생정보 조회 실패 " + e);
            throw new KnuApiCallFailedException();
        } catch (Exception e) {
            logger.error("학생정보 조회 시 알수없는 에러 " + e);
            throw new KnuApiCallFailedException();
        }
        return Optional.ofNullable(result);
    }

    @Override
    public KnuVeriusSatisfactionSurveyResponseDto getMyVeriusSatisfactionInfo(Map<String, String> veriusCookies, Integer page) {
        if(page <= 0) page = 1;

        //해당 참인재 쿠키로 정보를 긁어온다.
        Scraper<KnuVeriusSatisfactionSurveyScraperResponseDto> scraper = KnuVeriusSatisfactionSurveyScraper.builder()
                .apiEndpointSecretProperties(apiEndpointSecretProperties)
                .page(page)
                .veriusCookies(veriusCookies)
                .build();

        try {
            KnuVeriusSatisfactionSurveyScraperResponseDto response = scraper.scrap();
            return KnuVeriusSatisfactionSurveyResponseDto.from(response);
        } catch (IOException e) {
            logger.error("getMyVeriusSatisfactionInfo 크롤링 실패 " + e);
            throw new KnuApiCallFailedException();
        }
    }

    @Override
    public Optional<List<ResponseKnu.MyVeriusProgram>> getMyVeriusProgram(Map<String, String> veriusCookies, int page) {
        List<ResponseKnu.MyVeriusProgram> lists = new ArrayList<>();

        String url = apiEndpointSecretProperties.getCrawling().getVeriusMyProgram() + "?CURRENT_MENU_CODE=MENU0049&TOP_MENU_CODE=MENU0010&CURR_PAGE="+page;
        //크롤링하기
        try{
            Document doc = Jsoup.connect(url)
                    .userAgent(apiEndpointSecretProperties.getCrawling().getUserAgent())
                    .cookies(veriusCookies)
                    .get();

            Iterator<Element> rows = doc.select("#frm > div > div > table > tbody > tr").iterator();
            while(rows.hasNext()) {
                Element target = rows.next();
                Elements td = target.select("td"); // tr의 td들
                if(td.size()<2){
                    break;
                }
                Elements num= target.select("a");// title
                String title = td.get(2).text();     //제목
                String[] number = num.get(0).attr("href").split("'");
                String applicationDate1 = td.get(9).text();     //신청일
                String[] operationPeriod = td.get(4).text().split("~");     //운영기간
                String department = td.get(5).text();
                String link = null;         //제목 링크
                if("P".equals(number[3])){
                    link = apiEndpointSecretProperties.getCrawling().getVeriusMyProgramLinkTypeOne()
                            + "?CURRENT_MENU_CODE=MENU0049&TOP_MENU_CODE=MENU0010&PRM_SEQ="+number[1];
                }else{
                    link = apiEndpointSecretProperties.getCrawling().getVeriusMyProgramLinkTypeTwo()
                            + "?CURRENT_MENU_CODE=MENU0049&TOP_MENU_CODE=MENU0010&PRM_SEQ="+number[1];
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
            }
        } catch (IOException | ParseException e){
            logger.error("getMyVeriusProgram " + e);
        }
        return Optional.ofNullable(lists);
    }
    @Override
    public Optional<Map<String,Map<String,Integer>>> getMileage(Map<String, String> veriusCookies){
    Map<String,Map<String,Integer>> response = new HashMap<>();
    String url = apiEndpointSecretProperties.getCrawling().getVeriusMyMileage() + "?CURRENT_MENU_CODE=MENU0046&TOP_MENU_CODE=MENU0020";
        try {//크롤링
            Document doc = Jsoup.connect(url)
                    .userAgent(apiEndpointSecretProperties.getCrawling().getUserAgent())
                    .cookies(veriusCookies)
                    .get();
            Iterator<Element> target = doc.select("#right > div.user_sub_data > div:nth-child(2) > table > tbody > tr").iterator();
            while (target.hasNext()) {
                String[] td = target.next().text().split(" ");
                Map<String, Integer> item = response.get(td[0]);
                if(item == null) {
                    response.put(td[0], new HashMap<>());
                    item = response.get(td[0]);
                    // default 0
                    item.put("1", 0);
                    item.put("2", 0);
                }
                // 숫자 형변환 시도
                Integer integer;
                try {
                    integer = Integer.valueOf(td[2]);
                } catch (NumberFormatException numberFormatException) {
                    integer = 0;
                }
                item.put(td[1], integer);
            }
        }catch(IOException e) {
                logger.error("getMileage " + e);
        }
            return Optional.ofNullable(response);
        }
    }
