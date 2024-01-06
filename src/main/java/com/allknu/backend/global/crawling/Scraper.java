package com.allknu.backend.global.crawling;

import java.io.IOException;

public interface Scraper<T> {

    T scrap() throws IOException;
}
