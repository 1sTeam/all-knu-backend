package com.allknu.backend.auth.application;

import com.allknu.backend.knuapi.application.KnuMobileApiService;
import com.allknu.backend.knuapi.application.KnuVeriusApiService;
import com.allknu.backend.global.exception.errors.LoginFailedException;
import com.allknu.backend.knuapi.application.dto.SessionInfo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/*
 * @deprecated all-knu-auth 로 이관 예정
 */
@Deprecated
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final KnuMobileApiService knuMobileApiService;
    private final KnuVeriusApiService knuVeriusApiService;

    @Override
    public Optional<Map<String, String>> knuSsoLogin(String id, String password) {
        Map<String, String> cookies = null;
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
            logger.info("sso 로그인 성공");
            //로그인 성공 시 sso_token을 받는다. 이를 통해 로그인 성공 여부 판단
            if (cookies.get("sso_token") == null) {
                //로그인 실패
                logger.info("sso 로그인 실패");
                throw new LoginFailedException();
            }
        } catch (IOException e) {
            logger.error("sso login error " + e);
        }
        return Optional.ofNullable(cookies);
    }

    @Override
    public Optional<Map<String, String>> knuMobileLogin(String id, String password) {
        Map<String, String> data = new HashMap<>();
        data.put("user_id", id);
        data.put("user_pwd", password);

        Map<String, String> cookies = null;
        try {
            Connection.Response res = Jsoup.connect("***REMOVED***")
                    .method(Connection.Method.POST)
                    .data(data)
                    .ignoreContentType(true)
                    .userAgent("***REMOVED***")
                    .timeout(5000) // 5초
                    .execute();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(res.body()); // json mapper

            if (jsonNode.get("result").toString().equals("\"success\"")) {
                cookies = res.cookies();
                logger.info("모바일 로그인 성공");
            } else {
                logger.info("모바일 로그인 실패");
            }

        } catch (IOException e) {
            logger.error("error in mobile login(): " + e);
        }
        return Optional.ofNullable(cookies);
    }

    @Override
    public Optional<Map<String, String>> knuMobileRefreshSession(String id, String password, SessionInfo sessionInfo) {
        Map<String, String> cookies = null;
        if (sessionInfo != null) {
            cookies = sessionInfo.getMobileCookies();
        }
        if (knuMobileApiService.getMyScholarship(cookies).isPresent()) {
            // 모바일 세션은 유효하므로 발급하지 않음
            logger.debug("기존 모바일 세션이 유효해서 발급하지 않음");
            return Optional.ofNullable(cookies);
        }
        // 기존 모바일 세션이 유효하지 않으므로 새로 발급
        logger.info("기존 모바일 세션이 유효하지않아 새로 발급");
        return knuMobileLogin(id, password);
    }

    @Override
    public void knuMobileLogout(Map<String, String> cookies) {
        String url = "***REMOVED***";
        try {
            Connection.Response res = Jsoup.connect(url)
                    .method(Connection.Method.GET)
                    .ignoreContentType(true)
                    .cookies(cookies)
                    .userAgent("***REMOVED***")
                    .execute();
        } catch (IOException e) {
            logger.error("mobile logout error " + e);
        }
    }

    @Override
    public Optional<Map<String, String>> veriusLogin(Map<String, String> ssoCookies) {
        //sso쿠키로 참인재 로그인
        Map<String, String> veriusCookies = null;
        try {
            Connection.Response res = Jsoup.connect("***REMOVED***")
                    .method(Connection.Method.GET)
                    .cookies(ssoCookies)
                    .ignoreContentType(true)
                    .userAgent("***REMOVED***")
                    .timeout(10000) // 10초
                    .execute();
            veriusCookies = res.cookies();
            logger.info("참인재 로그인 성공");
        } catch (IOException e) {
            logger.error("veriusLogin() error: " + e);
        }
        return Optional.ofNullable(veriusCookies);
    }

    @Override
    public Optional<Map<String, String>> refreshVeriusLogin(Map<String, String> ssoCookies, SessionInfo sessionInfo) {
        try {
            // 기존 세션이 유효한지 검사, getStudentInfo를 호출해서
            if (knuVeriusApiService.getStudentInfo(sessionInfo.getVeriusCookies()).isPresent()) {
                return Optional.ofNullable(sessionInfo.getVeriusCookies());
            }
        } catch (Exception e) {
            logger.info("참인재 세션 갱신을 위해 getStudentInfo를 호출했으나 실패, 세션을 새로 갱신" + e);
        }
        // 새로 로그인
        return veriusLogin(ssoCookies);
    }

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
        } catch (IOException e) {
            logger.error("knuInfoSystemLogin() error: " + e);
        }
        return Optional.ofNullable(knuInfoSystemCookies);
    }
}
