package com.allknu.backend.provider.service;

import com.allknu.backend.core.service.CrawlingServiceInterface;
import com.allknu.backend.web.dto.ResponseCrawling;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class CrawlingService implements CrawlingServiceInterface {

    private ObjectMapper objectMapper;

    @PostConstruct
    void init() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public Optional<List<ResponseCrawling.UnivNotice>> getUnivNotice(int pageNum) {
        List<ResponseCrawling.UnivNotice> lists = new ArrayList<>();

        String url = "***REMOVED***?paginationInfo.currentPageNo="
                +pageNum+"&searchMenuSeq=0&searchType=&searchValue=";

        try {
            Document doc = Jsoup.connect(url).get();
            Elements elem = doc.select("div.tbody > ul");

            Iterator<Element> links = elem.select("a.detailLink").iterator();

            while (links.hasNext()) {
                Element target = links.next();

                JsonNode jsonNode = objectMapper.readTree(target.attr("data-params"));
                String encMenuSeq = jsonNode.get("encMenuSeq").asText();
                String encMenuBoardSeq = jsonNode.get("encMenuBoardSeq").asText();
                String link = "***REMOVED***?scrtWrtiYn=false&encMenuSeq="
                        + encMenuSeq + "&encMenuBoardSeq=" + encMenuBoardSeq;

                ResponseCrawling.UnivNotice notice = ResponseCrawling.UnivNotice.builder()
                        .title(target.text())
                        .link(link)
                        .build();

                lists.add(notice);
            }

        } catch (IOException e) {
            System.out.println(e);
        }

        return Optional.ofNullable(lists);
    }
}
