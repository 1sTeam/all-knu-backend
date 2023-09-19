package com.allknu.backend.core.service;

import com.allknu.backend.web.dto.SessionInfo;

import java.util.Map;
import java.util.Optional;

/*
 * @deprecated all-knu-auth 로 이관 예정
 */
@Deprecated
public interface AuthService {
    Optional<Map<String, String>> knuSsoLogin(String id, String password);
    Optional<Map<String, String>> knuMobileLogin(String id, String password);
    Optional<Map<String, String>> knuMobileRefreshSession(String id, String password, SessionInfo sessionInfo);
    void knuMobileLogout(Map<String, String> cookies);
    Optional<Map<String, String>> veriusLogin(Map<String, String> ssoCookies);
    Optional<Map<String, String>> refreshVeriusLogin(Map<String, String> ssoCookies, SessionInfo sessionInfo);
    Optional<Map<String, String>> knuInfoSystemLogin(Map<String, String> ssoCookies); // 종정시 로그인
}
