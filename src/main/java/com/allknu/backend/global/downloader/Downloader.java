package com.allknu.backend.global.downloader;

import org.jsoup.nodes.Document;

import java.io.IOException;

public interface Downloader {

    Document download(String url) throws IOException;
}
