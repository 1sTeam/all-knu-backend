package com.allknu.backend.knuapi.application;

import com.allknu.backend.knuapi.application.dto.CalendarResponseDto;
import com.allknu.backend.knuapi.application.dto.EventNoticeResponseDto;
import com.allknu.backend.knuapi.application.dto.UnivNoticeResponseDto;
import com.allknu.backend.knuapi.domain.EventNoticeType;
import com.allknu.backend.knuapi.domain.MajorNoticeType;
import com.allknu.backend.knuapi.domain.UnivNoticeType;
import com.allknu.backend.knuapi.application.dto.ResponseCrawling;
import com.allknu.backend.knuapi.domain.scraper.dto.UnivNoticeScraperResponseDto;

import java.util.List;
import java.util.Optional;

public interface CrawlingService {
    UnivNoticeResponseDto getUnivNotice(int pageNum, UnivNoticeType type);
    Optional<List<ResponseCrawling.UnivNotice>> getMajorDefaultTemplateNotice(int pageNum, MajorNoticeType type);
    CalendarResponseDto getKnuCalendar();
    EventNoticeResponseDto getEventNotice(int pageNum, EventNoticeType type);

}
