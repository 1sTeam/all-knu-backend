package com.allknu.backend.provider.service;

import com.allknu.backend.core.service.KnuInfoSystemService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KnuInfoSystemServiceImpl implements KnuInfoSystemService {
    private static final Logger logger = LoggerFactory.getLogger(KnuInfoSystemServiceImpl.class);

    @Override
    public Optional<Map<String, String>> knuInfoSystemLogin(Map<String, String> ssoCookies) {
        //sso쿠키로 종합정보시스템 로그인
        Map<String, String> knuInfoSystemCookies = null;
        try {
            Connection.Response res = Jsoup.connect("***REMOVED***")
                    .method(Connection.Method.GET)
                    .cookies(ssoCookies)
                    .ignoreContentType(true)
                    .userAgent("***REMOVED***")
                    .timeout(5000) // 5초
                    .execute();
            knuInfoSystemCookies = res.cookies();
            logger.info("종정시 로그인 성공");
        }catch (IOException e) {
            logger.error("knuInfoSystemLogin() error: " + e);
        }
        return Optional.ofNullable(knuInfoSystemCookies);
    }
}
