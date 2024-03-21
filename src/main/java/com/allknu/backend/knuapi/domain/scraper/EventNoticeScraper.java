package com.allknu.backend.knuapi.domain.scraper;

import com.allknu.backend.global.asset.ApiEndpointSecretProperties;
import com.allknu.backend.global.scraper.Scraper;
import com.allknu.backend.knuapi.domain.scraper.dto.EventNoticeScraperResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;


@RequiredArgsConstructor
public class EventNoticeScraper implements Scraper<EventNoticeScraperResponseDto> {
    private final ObjectMapper objectMapper;
    private final ApiEndpointSecretProperties apiEndpointSecretProperties;
    private static final Logger log = LoggerFactory.getLogger(EventNoticeScraper.class);

    @Override
    public EventNoticeScraperResponseDto scrape(Document document) throws Exception {
        List<EventNoticeScraperResponseDto.ScraperEventDetail> eventNoticeList = new ArrayList<>();

        Iterator<Element> rows = document.select("div.tbody > ul > li").iterator();
        if (!rows.hasNext()) {
            throw new Exception("조회 결과가 없습니다.");
        }

        while (rows.hasNext()) {
            Element target = rows.next();
            if ("NO_RESULT".equals(target.attr("class"))) { // 페이지에 조회 자료가 없는 경우
                continue;
            }
            Elements dl = target.select("div > dl"); // li 안에 div 안에 dl들
            Elements dt = dl.select("dt"); // title
            Elements span = dl.select("dd > span"); // 작성자, 등록일, 조회수

            String title = dt.get(0).text(); // 제목 title

            // JSON 파싱 로직
            Element linkElement = dt.get(0).selectFirst("a.detailLink"); // 링크
            String encMenuSeq, encMenuBoardSeq;
            try {
                JsonNode jsonNode = objectMapper.readTree(linkElement.attr("data-params"));
                encMenuSeq = jsonNode.get("encMenuSeq").asText();
                encMenuBoardSeq = jsonNode.get("encMenuBoardSeq").asText();
            } catch (JsonProcessingException e) {
                log.error("JSON 파싱 에러: ", e);
                throw new Exception("JSON 파싱 에러: " + e.getMessage());
            }

            String link = apiEndpointSecretProperties.getCrawling().getEventNoticeItem()
                    + "?scrtWrtiYn=false&encMenuSeq="
                    + encMenuSeq
                    + "&encMenuBoardSeq="
                    + encMenuBoardSeq;

            String writer = span.get(0).text().substring(3); // 작성자
            String date = span.get(1).text().substring(4); // date
            String views = span.get(2).text().substring(4); // views

            EventNoticeScraperResponseDto.ScraperEventDetail eventDetail = EventNoticeScraperResponseDto.ScraperEventDetail.builder()
                    .title(title)
                    .link(link)
                    .date(date)
                    .writer(writer)
                    .views(views)
                    .build();

            eventNoticeList.add(eventDetail);
        }

        return new EventNoticeScraperResponseDto(eventNoticeList);
    }
}
