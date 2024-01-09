package com.allknu.backend.global.downloader;

import com.allknu.backend.global.asset.ApiEndpointSecretProperties;
import com.allknu.backend.knuapi.application.CrawlingServiceImpl;
import com.allknu.backend.knuapi.domain.UnivNoticeType;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Iterator;

@Service
@RequiredArgsConstructor
public class GeneralDownloader implements Downloader{

    private static final Logger log = LoggerFactory.getLogger(CrawlingServiceImpl.class);
    private final ApiEndpointSecretProperties apiEndpointSecretProperties;

    public Iterator<Element> generalDownloader(int pageNum, UnivNoticeType type){
        //type에 따라 전체, 학사, 장학, 학습/상담, 취창업 공지 크롤링
        String url = apiEndpointSecretProperties.getCrawling().getUnivNotice() + "?paginationInfo.currentPageNo="
                +pageNum+"&searchMenuSeq=" + type.getSearchMenuNumber() + "&searchType=&searchValue=";
        Iterator<org.jsoup.nodes.Element> rows = null;
        try {
            //예외와 상관없이 자원 할당 해제하도록 변경
            Document doc = Jsoup.connect(url).execute().parse();
            rows = doc.select("div.tbody > ul").iterator();
        } catch (IOException e) {
            log.error("학교공지 crawling error + " + e);
        }
        return rows;
    }
}
