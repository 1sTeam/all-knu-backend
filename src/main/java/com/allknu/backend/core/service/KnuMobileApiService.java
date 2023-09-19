package com.allknu.backend.core.service;

import com.allknu.backend.web.dto.ResponseKnu;
import com.allknu.backend.web.dto.SessionInfo;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface KnuMobileApiService {
    Optional<JsonNode> getKnuApiJsonData(String url, Map<String,String> cookies);
    Optional<ResponseKnu.TimeTable> getTimeTable(Map<String, String> cookies);
    Optional<ResponseKnu.PeriodUniv> getPeriodOfUniv(Map<String, String> cookies);
    Optional<ResponseKnu.Grade> getGrade(Map<String, String> cookies, String year, String semester);
    Optional<List<ResponseKnu.CalendarItem>> getKnuCalendar();
    Optional<List<ResponseKnu.ScholarshipItem>> getMyScholarship(Map<String, String> cookies);
    Optional<ResponseKnu.Tuition> getMyTuition(Map<String, String> cookies, Integer year, Integer semester);
    Optional<List<ResponseKnu.Staff>> getKnuStaffInfo();
}
