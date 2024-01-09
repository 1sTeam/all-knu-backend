package com.allknu.backend.knuapi.domain;

import com.allknu.backend.global.asset.ApiEndpointSecretProperties;
import com.allknu.backend.knuapi.application.CrawlingServiceImpl;
import com.allknu.backend.knuapi.application.dto.ResponseCrawling;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KnuNoticeScraperImpl implements KnuNoticeScraper{
    private static final Logger log = LoggerFactory.getLogger(CrawlingServiceImpl.class);
    private final ApiEndpointSecretProperties apiEndpointSecretProperties;
    private final ObjectMapper objectMapper;
    @Override
    public List<ResponseCrawling.UnivNotice> scrapKnuNotice(Iterator<Element> rows) {
        List<ResponseCrawling.UnivNotice> lists = new ArrayList<>();
        try {
            while(rows.hasNext()) {
                Element target = rows.next();
                Elements li = target.select("li"); // ul 안의 li들

                String number = li.get(0).text(); // 게시글번호 li
                if(!StringUtil.isNumeric(number)) {
                    //넘버가 숫자가 아니라면 필독공지임 이거는 패스
                    continue;
                }

                //생성과 할당 동시에 하도록 변경
                Element linkElement = li.get(1).selectFirst("a.detailLink"); // 링크li
                JsonNode jsonNode = objectMapper.readTree(linkElement.attr("data-params"));
                String link = apiEndpointSecretProperties.getCrawling().getUnivNoticeItem()
                        + "?scrtWrtiYn=false&encMenuSeq=" + jsonNode.get("encMenuSeq").asText()
                        + "&encMenuBoardSeq=" + jsonNode.get("encMenuBoardSeq").asText();

                String title = linkElement.text();
                String writeType = li.get(2).text(); //구분 li
                String writer = li.get(4).text(); // 작성자
                String date = li.get(5).text(); // date
                String views = li.get(6).text(); // views

                ResponseCrawling.UnivNotice notice = ResponseCrawling.UnivNotice.builder()
                        .link(link)
                        .date(date)
                        .number(number)
                        .writer(writer)
                        .type(writeType)
                        .views(views)
                        .title(title)
                        .build();

                lists.add(notice);
        }
        } catch (IOException e) {
            log.error("학교공지 crawling error + " + e);
        }

        return lists;
    }
}
