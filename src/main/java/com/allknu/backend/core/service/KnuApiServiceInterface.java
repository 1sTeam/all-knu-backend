package com.allknu.backend.core.service;

import com.allknu.backend.web.dto.RequestKnu;
import com.allknu.backend.web.dto.ResponseKnu;

import java.util.Map;
import java.util.Optional;

public interface KnuApiServiceInterface {
    Optional<Map<String, String>> login(String id, String password);
    void logout(Map<String, String> cookies);
    Optional<ResponseKnu.TimeTable> getTimeTable(Map<String, String> cookies);
}
