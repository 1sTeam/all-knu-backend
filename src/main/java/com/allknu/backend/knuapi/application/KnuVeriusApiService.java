package com.allknu.backend.knuapi.application;

import com.allknu.backend.knuapi.application.dto.KnuVeriusSatisfactionSurveyResponseDto;
import com.allknu.backend.knuapi.application.dto.ResponseKnu;

import java.util.List;
import java.util.Map;
import java.util.Optional;

//참인재시스템
public interface KnuVeriusApiService {
    Optional<Map<String, String>> getStudentInfo(Map<String, String> veriusCookies);
    KnuVeriusSatisfactionSurveyResponseDto getMyVeriusSatisfactionInfo(Map<String, String> veriusCookies, Integer page);
    Optional<List<ResponseKnu.MyVeriusProgram>> getMyVeriusProgram(Map<String, String> veriusCookies, int page);
    Optional<Map<String,Map<String,Integer>>> getMileage(Map<String, String> veriusCookies);
}
