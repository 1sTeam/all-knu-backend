package com.allknu.backend.global.downloader;

import com.allknu.backend.knuapi.domain.UnivNoticeType;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Iterator;


public interface Downloader {
    public Document generalDownloader(int pageNum, UnivNoticeType type);
}
