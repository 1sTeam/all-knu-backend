package com.allknu.backend.knuapi.domain;

import com.allknu.backend.knuapi.application.dto.ResponseCrawling;
import org.jsoup.nodes.Element;

import java.util.Iterator;
import java.util.List;

public interface KnuNoticeScraper extends Scraper {
    public List<ResponseCrawling.UnivNotice> scrapKnuNotice(Iterator<Element> rows);
}
