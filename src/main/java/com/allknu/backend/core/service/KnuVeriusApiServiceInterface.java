package com.allknu.backend.core.service;

import java.util.Map;
import java.util.Optional;

//참인재시스템
public interface KnuVeriusApiServiceInterface {
    Optional<Map<String, String>> getStudentInfo(Map<String, String> ssoCookies);
}
