package com.allknu.backend.core.service;

import com.allknu.backend.web.dto.ResponseKnu;

import java.util.List;
import java.util.Map;
import java.util.Optional;

//참인재시스템
public interface KnuVeriusApiServiceInterface {
    Optional<Map<String, String>> getStudentInfo(Map<String, String> ssoCookies);
    Optional<List<ResponseKnu.VeriusSatisfaction>> getMyVeriusSatisfactionInfo(Map<String, String> ssoCookies, Integer page);
}
