package com.allknu.backend.global.security.interceptor;

import com.allknu.backend.global.exception.errors.CustomJwtRuntimeException;
import com.allknu.backend.global.security.JwtAuthToken;
import com.allknu.backend.global.security.JwtAuthTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class AuthInterceptor implements HandlerInterceptor
{
    private final JwtAuthTokenProvider jwtAuthTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if(request.getMethod().equals("OPTIONS")) {
            //request method가 OPTIONS일 경우 JWT를 체크하지 않는 방향으로(개발 블로그 참고)
            return true;
        }
        Optional<String> token = jwtAuthTokenProvider.resolveToken(request);
        if(token.isPresent()) {
            //토큰이 존재한다면 해당 string을 객체로 변환해서 검증한다
            JwtAuthToken jwtAuthToken = jwtAuthTokenProvider.convertAuthToken(token.get());
            if(jwtAuthToken.validate()) {
                return true;
            }
            throw new CustomJwtRuntimeException();
        }
        throw new CustomJwtRuntimeException();
    }


}
