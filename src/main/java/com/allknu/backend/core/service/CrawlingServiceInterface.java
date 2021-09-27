package com.allknu.backend.core.service;

import com.allknu.backend.web.dto.ResponseCrawling;

import java.util.List;
import java.util.Optional;

public interface CrawlingServiceInterface {
    Optional<List<ResponseCrawling.UnivNotice>> getUnivNotice(int pageNum);
}
