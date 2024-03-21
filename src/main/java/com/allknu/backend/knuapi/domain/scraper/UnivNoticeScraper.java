package com.allknu.backend.knuapi.domain.scraper;

import com.allknu.backend.global.asset.ApiEndpointSecretProperties;
import com.allknu.backend.global.scraper.Scraper;
import com.allknu.backend.knuapi.domain.scraper.dto.UnivNoticeScraperResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

@RequiredArgsConstructor
public class UnivNoticeScraper implements Scraper<UnivNoticeScraperResponseDto> {
    private final ObjectMapper objectMapper;
    private final ApiEndpointSecretProperties apiEndpointSecretProperties;
    private static final Logger log = LoggerFactory.getLogger(UnivNoticeScraper.class);

    @Override
    public UnivNoticeScraperResponseDto scrape(Document document) throws Exception {
        List<UnivNoticeScraperResponseDto.ScraperUnivNoticeDto> univNoticeList = new ArrayList<>();

        Iterator<Element> rows = document.select("div.tbody > ul").iterator();
        if (!rows.hasNext()) {
            throw new Exception("조회 결과가 없습니다.");
        }

        while (rows.hasNext()) {
            Element target = rows.next();
            Elements li = target.select("li");

            String number = li.get(0).text();
            if (!StringUtil.isNumeric(number)) {
                //넘버가 숫자가 아니라면 필독공지
                continue;
            }

            Element linkElement = li.get(1).selectFirst("a.detailLink");
            String encMenuSeq, encMenuBoardSeq;
            try {
                JsonNode jsonNode = objectMapper.readTree(linkElement.attr("data-params"));
                encMenuSeq = jsonNode.get("encMenuSeq").asText();
                encMenuBoardSeq = jsonNode.get("encMenuBoardSeq").asText();
            } catch (JsonProcessingException e) {
                log.error("JSON 파싱 에러: ", e);
                throw new Exception("JSON 파싱 에러: " + e.getMessage());
            }

            String link = apiEndpointSecretProperties.getCrawling().getUnivNoticeItem() + "?scrtWrtiYn=false&encMenuSeq="
                    + encMenuSeq + "&encMenuBoardSeq=" + encMenuBoardSeq;

            String title = linkElement.text();
            String writeType = li.get(2).text();
            String writer = li.get(4).text();
            String date = li.get(5).text();
            String views = li.get(6).text();

            UnivNoticeScraperResponseDto.ScraperUnivNoticeDto notice = UnivNoticeScraperResponseDto.ScraperUnivNoticeDto.builder()
                    .link(link)
                    .date(date)
                    .number(number)
                    .writer(writer)
                    .type(writeType)
                    .views(views)
                    .title(title)
                    .build();

            univNoticeList.add(notice);
        }

        return new UnivNoticeScraperResponseDto(univNoticeList);
    }
}
