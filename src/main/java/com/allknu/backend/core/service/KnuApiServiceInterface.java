package com.allknu.backend.core.service;

import java.util.Map;
import java.util.Optional;

public interface KnuApiServiceInterface {
    Optional<Map<String, String>> login(String id, String password);
    void logout(Map<String, String> cookies);
}
