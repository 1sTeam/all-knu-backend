package com.allknu.backend.core.service;

import com.allknu.backend.core.types.MajorNoticeType;
import com.allknu.backend.core.types.UnivNoticeType;
import com.allknu.backend.web.dto.ResponseCrawling;

import java.util.List;
import java.util.Optional;

public interface CrawlingServiceInterface {
    Optional<List<ResponseCrawling.UnivNotice>> getUnivNotice(int pageNum, UnivNoticeType type);
    Optional<List<ResponseCrawling.UnivNotice>> getMajorDefaultTemplateNotice(int pageNum, MajorNoticeType type);
    Optional<List<ResponseCrawling.Calendar>> getKnuCalendar();
}
