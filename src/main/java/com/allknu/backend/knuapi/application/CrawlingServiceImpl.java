package com.allknu.backend.knuapi.application;

import com.allknu.backend.global.asset.ApiEndpointSecretProperties;
import com.allknu.backend.knuapi.application.dto.CalendarResponseDto;
import com.allknu.backend.knuapi.domain.*;
import com.allknu.backend.knuapi.application.dto.ResponseCrawling;
import com.allknu.backend.knuapi.domain.scraper.EventNoticeScraper;
import com.allknu.backend.knuapi.domain.scraper.KnuCalenderScraper;
import com.allknu.backend.global.crawling.Scraper;
import com.allknu.backend.knuapi.domain.scraper.UnivNoticeScraper;
import com.allknu.backend.knuapi.domain.scraper.dto.EventNoticeResponseDto;
import com.allknu.backend.knuapi.domain.scraper.dto.KnuCalenderScraperResponseDto;
import com.allknu.backend.knuapi.domain.scraper.dto.UnivNoticeResponseDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import org.jsoup.Jsoup;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CrawlingServiceImpl implements CrawlingService {

    private static final Logger log = LoggerFactory.getLogger(CrawlingServiceImpl.class);
    private final ObjectMapper objectMapper;
    private final ApiEndpointSecretProperties apiEndpointSecretProperties;

    @Override
    public UnivNoticeResponseDto getUnivNotice(int pageNum, UnivNoticeType type) {
        UnivNoticeScraper scraper = new UnivNoticeScraper(objectMapper, apiEndpointSecretProperties);

        try {
            // 웹 페이지의 URL을 생성합니다.
            String url = apiEndpointSecretProperties.getCrawling().getUnivNotice()
                    + "?paginationInfo.currentPageNo=" + pageNum
                    + "&searchMenuSeq=" + type.getSearchMenuNumber()
                    + "&searchType=&searchValue=";

            // URL로부터 Document 객체를 생성합니다.
            Document document = Jsoup.connect(url).get();

            // Document 객체를 매개변수로 scrape 메서드를 호출합니다.
            return scraper.scrape(document);
        } catch (Exception e) {
            log.error("학교공지 crawling error ", e);
            return new UnivNoticeResponseDto(new LinkedHashMap<>());
        }
    }

    @Override
    public EventNoticeResponseDto getEventNotice(int pageNum, EventNoticeType type) {
        EventNoticeScraper scraper = new EventNoticeScraper(objectMapper, apiEndpointSecretProperties);

        try {
            // 웹 페이지의 URL을 생성합니다.
            String url = apiEndpointSecretProperties.getCrawling().getEventNotice()
                    + "?paginationInfo.currentPageNo=" + pageNum
                    + "&searchMenuSeq=" + type.getSearchMenuNumber()
                    + "&searchType=&searchValue=";

            // URL로부터 Document 객체를 생성합니다.
            Document document = Jsoup.connect(url).get();

            // Document 객체를 매개변수로 scrape 메서드를 호출합니다.
            return scraper.scrape(document);
        } catch (Exception e) {
            log.error("행사공지 error ", e);
            return new EventNoticeResponseDto(new LinkedHashMap<>());
        }
    }



    @Override
    public Optional<List<ResponseCrawling.UnivNotice>> getMajorDefaultTemplateNotice(int pageNum, MajorNoticeType type) {
        List<ResponseCrawling.UnivNotice> lists = new ArrayList<>();
        String url = type.getUrl() + "?paginationInfo.currentPageNo=" + pageNum;

        try {
            Document doc = Jsoup.connect(url).get();

            //구분이 있는 학과 공지도 있고 구분이 없는 학과 공지가 있다.
            int tableHeadSize = doc.select("div.thead li").size(); // 테이블 헤드 길이 계산, 6개면 세부구분이 없는 학과

            Iterator<Element> rows = doc.select("div.tbody > ul").iterator();
            while(rows.hasNext()) {
                Element target = rows.next();
                Elements li = target.select("li"); // ul 안의 li들

                String number = li.get(0).text(); // 게시글번호 li
                if(!StringUtil.isNumeric(number)) {
                    //넘버가 숫자가 아니라면 필독공지임 이거는 패스
                    continue;
                }

                Element linkElement = li.get(1).selectFirst("a.detailLink"); // 링크li
                JsonNode jsonNode = objectMapper.readTree(linkElement.attr("data-params"));
                String encMenuSeq = jsonNode.get("encMenuSeq").asText();
                String encMenuBoardSeq = jsonNode.get("encMenuBoardSeq").asText();
                String link = type.getBoardUrl() + "?encMenuSeq=" + encMenuSeq + "&encMenuBoardSeq=" + encMenuBoardSeq;

                ResponseCrawling.UnivNotice notice = null;
                if(tableHeadSize == 7) {
                    //소응 처럼 세부 구분이 있는 학과 공지사항
                    String title = linkElement.text();
                    String writeType = li.get(2).text(); //구분 li
                    String writer = li.get(4).text(); // 작성자
                    String date = li.get(5).text(); // date
                    String views = li.get(6).text(); // views

                    notice = ResponseCrawling.UnivNotice.builder()
                            .link(link)
                            .date(date)
                            .number(number)
                            .writer(writer)
                            .type(writeType)
                            .views(views)
                            .title(title)
                            .build();
                } else if(tableHeadSize == 5) {
                    //글경은 5개..ㅎㅎ
                    String title = linkElement.text();
                    String views = li.get(2).text(); // views
                    String writer = li.get(3).text(); // 작성자
                    String date = li.get(4).text(); // date

                    notice = ResponseCrawling.UnivNotice.builder()
                            .link(link)
                            .date(date)
                            .number(number)
                            .writer(writer)
                            .views(views)
                            .title(title)
                            .build();
                } else {
                    //교육학과처럼 세부 구분이 없는 학과 공지사항
                    String title = linkElement.text();
                    String writer = li.get(3).text(); // 작성자
                    String date = li.get(4).text(); // date
                    String views = li.get(5).text(); // views

                    notice = ResponseCrawling.UnivNotice.builder()
                            .link(link)
                            .date(date)
                            .number(number)
                            .writer(writer)
                            .views(views)
                            .title(title)
                            .build();
                }
                lists.add(notice);
            }

        } catch (IOException e) {
            log.error("학과공지 error " + e);
        }
        return Optional.ofNullable(lists);
    }

    @Override
    public CalendarResponseDto getKnuCalendar(){
        Scraper<KnuCalenderScraperResponseDto> scraper = KnuCalenderScraper.builder()
                .apiEndpointSecretProperties(apiEndpointSecretProperties)
                .build();
        try {
            KnuCalenderScraperResponseDto knuCalenderScraperResponseDto = scraper.scrap();
            return CalendarResponseDto.from(knuCalenderScraperResponseDto);
        } catch (IOException e) {
            log.error("knu calender 크롤링 실패");
            return CalendarResponseDto.empty();
        }
    }
}