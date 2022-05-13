package com.allknu.backend.provider.service;

import com.allknu.backend.core.service.KnuApiService;
import com.allknu.backend.exception.errors.LoginFailedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class KnuApiServiceImpl implements KnuApiService {
    private static final Logger log = LoggerFactory.getLogger(KnuApiServiceImpl.class);

    @Override
    public Optional<Map<String, String>> ssoLogin(String id, String password) {
        Map<String, String> cookies = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            //ssoLogin jsp를 호출한다.
            Map<String, String> data = new HashMap<>();
            data.put("uid", id);
            data.put("password", password);
            data.put("gid", "gid_web");
            data.put("returl", "***REMOVED***");

            Connection.Response ssoLoginRes = Jsoup.connect("***REMOVED***")
                    .method(Connection.Method.POST)
                    .ignoreContentType(true)
                    .data(data)
                    .userAgent("***REMOVED***")
                    .timeout(5000) // 5초
                    .execute();

            cookies = ssoLoginRes.cookies();
            log.info("sso 로그인 성공");
            //로그인 성공 시 sso_token을 받는다. 이를 통해 로그인 성공 여부 판단
            if(cookies.get("sso_token") == null) {
                //로그인 실패
                log.info("sso 로그인 실패");
                throw new LoginFailedException();
            }
        } catch (IOException e) {
            log.error("sso login error " + e);
            throw new LoginFailedException();
        }
        return Optional.ofNullable(cookies);
    }
}
