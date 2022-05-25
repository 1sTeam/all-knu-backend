package com.allknu.backend.core.service;

import java.util.Map;
import java.util.Optional;

public interface KnuInfoSystemService {
    Optional<Map<String, String>> knuInfoSystemLogin(Map<String, String> ssoCookies); // 종정시 로그인
}
