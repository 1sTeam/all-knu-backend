package com.allknu.backend.core.service;

import com.allknu.backend.web.dto.RequestKnu;
import com.allknu.backend.web.dto.ResponseKnu;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Map;
import java.util.Optional;

public interface KnuApiServiceInterface {
    Optional<JsonNode> getKnuApiJsonData(String url, Map<String,String> cookies);
    Optional<Map<String, String>> login(String id, String password);
    void logout(Map<String, String> cookies);
    Optional<ResponseKnu.TimeTable> getTimeTable(Map<String, String> cookies);
    Optional<ResponseKnu.PeriodUniv> getPeriodOfUniv(Map<String, String> cookies);
    Optional<ResponseKnu.Grade> getGrade(Map<String, String> cookies, String year, String semester);
}
