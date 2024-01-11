package com.allknu.backend.global.downloader;

import com.allknu.backend.global.asset.ApiEndpointSecretProperties;
import com.allknu.backend.knuapi.application.CrawlingServiceImpl;
import com.allknu.backend.knuapi.domain.UnivNoticeType;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class GeneralDownloader implements Downloader{

    private static final Logger log = LoggerFactory.getLogger(CrawlingServiceImpl.class);
    private final ApiEndpointSecretProperties apiEndpointSecretProperties;

    public Document generalDownloader(int pageNum, UnivNoticeType type){
        //type에 따라 전체, 학사, 장학, 학습/상담, 취창업 공지 크롤링
        String url = apiEndpointSecretProperties.getCrawling().getUnivNotice() + "?paginationInfo.currentPageNo="
                +pageNum+"&searchMenuSeq=" + type.getSearchMenuNumber() + "&searchType=&searchValue=";
        Document doc = null;
        try {
            //예외와 상관없이 자원 할당 해제하도록 변경
            doc = Jsoup.connect(url).execute().parse();
        } catch (IOException e) {
            log.error("학교공지 crawling error + " + e);
        }
        return doc;
    }
}
