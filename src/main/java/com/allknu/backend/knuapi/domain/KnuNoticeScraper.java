package com.allknu.backend.knuapi.domain;

import com.allknu.backend.knuapi.application.dto.ResponseCrawling;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Iterator;
import java.util.List;

public interface KnuNoticeScraper extends Scraper {
    public ResponseCrawling.UnivNotice scrapKnuNotice(Elements li);
}
