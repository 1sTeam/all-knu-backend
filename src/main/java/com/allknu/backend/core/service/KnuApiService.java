package com.allknu.backend.core.service;

import java.util.Map;
import java.util.Optional;

public interface KnuApiService {
    Optional<Map<String, String>> ssoLogin(String id, String password);

}
