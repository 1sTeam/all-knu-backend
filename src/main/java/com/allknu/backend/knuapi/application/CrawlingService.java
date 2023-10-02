package com.allknu.backend.knuapi.application;

import com.allknu.backend.knuapi.domain.EventNoticeType;
import com.allknu.backend.knuapi.domain.MajorNoticeType;
import com.allknu.backend.knuapi.domain.UnivNoticeType;
import com.allknu.backend.knuapi.application.dto.ResponseCrawling;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CrawlingService {
    Optional<List<ResponseCrawling.UnivNotice>> getUnivNotice(int pageNum, UnivNoticeType type);
    Optional<List<ResponseCrawling.UnivNotice>> getMajorDefaultTemplateNotice(int pageNum, MajorNoticeType type);
    Optional<Map<String, List<ResponseCrawling.Schedule>>> getKnuCalendar();
    Optional<List<ResponseCrawling.EventNotice>> getEventNotice(int pageNum, EventNoticeType type);

}
