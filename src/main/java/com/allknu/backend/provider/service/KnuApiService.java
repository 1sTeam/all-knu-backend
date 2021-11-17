package com.allknu.backend.provider.service;

import com.allknu.backend.core.service.KnuApiServiceInterface;
import com.allknu.backend.exception.errors.LoginFailedException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class KnuApiService implements KnuApiServiceInterface {
    @Override
    public Optional<Map<String, String>> ssoLogin(String id, String password) {
        Map<String, String> cookies = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            //setLoginCookie.jsp에 학번만 넣어서 쿠키를 설정받는다.
            String setLoginCookieUrl = "https://knusso.kangnam.ac.kr/setLoginCookie.jsp?id=" + id;
            Connection.Response setLoginCookieRes = Jsoup.connect(setLoginCookieUrl)
                    .method(Connection.Method.GET)
                    .ignoreContentType(true)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.61 Safari/537.36")
                    .execute();
            Map<String, String> setLoginCookie = setLoginCookieRes.cookies();
            //그 쿠키를 이용해 ssoLogin jsp를 호출한다.
            String ssoLoginUrl = "https://knusso.kangnam.ac.kr/sso/pmi-sso-login-uid-password.jsp?"
                    +"gid=" + "gid_web"
                    +"&returl=" + "https://web.kangnam.ac.kr/sso/index.jsp"
                    +"&uid=" + id
                    +"&password=" + password;

            Connection.Response ssoLoginRes = Jsoup.connect(ssoLoginUrl)
                    .method(Connection.Method.GET)
                    .ignoreContentType(true)
                    .cookies(setLoginCookie)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.61 Safari/537.36")
                    .execute();

            cookies = ssoLoginRes.cookies();

            //로그인 성공 시 sso_token을 받는다. 이를 통해 로그인 성공 여부 판단
            if(cookies.get("sso_token") == null) {
                //로그인 실패
                throw new LoginFailedException();
            }
        } catch (IOException e) {
            System.out.println(e);
            throw new LoginFailedException();
        }
        return Optional.ofNullable(cookies);
    }
}
