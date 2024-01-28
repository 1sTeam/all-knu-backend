package com.allknu.backend.global.scraper;

import org.jsoup.nodes.Document;

import java.util.List;

public interface Scraper<T> {
    T scrape(Document document) throws Exception;
}