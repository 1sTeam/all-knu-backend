package com.allknu.backend.global.downloader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class CrawlingDownloader implements Downloader{
    @Override
    public Document download(String url) throws IOException {
        return Jsoup.connect(url).get();
    }
}
