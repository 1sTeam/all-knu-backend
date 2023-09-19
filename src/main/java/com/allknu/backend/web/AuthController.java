package com.allknu.backend.web;

import com.allknu.backend.core.service.AuthService;
import com.allknu.backend.core.service.KnuVeriusApiService;
import com.allknu.backend.exception.errors.KnuApiCallFailedException;
import com.allknu.backend.exception.errors.KnuReadTimeOutException;
import com.allknu.backend.exception.errors.LoginFailedException;
import com.allknu.backend.web.dto.CommonResponse;
import com.allknu.backend.web.dto.RequestKnu;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/*
 * @deprecated all-knu-auth 로 이관 예정
 */
@Deprecated
@RestController
@RequiredArgsConstructor
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;
    private final KnuVeriusApiService knuVeriusApiService;

    /**
     * 사용 중 로그인 API
     */
    @PostMapping("/knu/login")
    public ResponseEntity<CommonResponse> knuLogin(@Valid @RequestBody RequestKnu.Login loginDto) {
        if (loginDto.getId().charAt(0) == '1') {
            logger.info("학번/사번이 1로 시작하는 사람이 로그인 시도");
            throw new LoginFailedException(); // 사번이 1로 시작하는 교직원은 이용불가
        }
        //모바일 로그인
        Map<String, String> mobileCookies = authService.knuMobileLogin(loginDto.getId(), loginDto.getPassword()).orElseThrow(() -> new LoginFailedException());
        //통합 SSO 로그인
        Map<String, String> ssoCookies = authService.knuSsoLogin(loginDto.getId(), loginDto.getPassword()).orElseThrow(() -> new KnuReadTimeOutException("sso"));
        // 참인재 로그인
        Map<String, String> veriusCookies = authService.veriusLogin(ssoCookies).orElseThrow(() -> new KnuReadTimeOutException("verius"));
        //학생 정보 긁어오기
        Map<String, String> studentInfo = knuVeriusApiService.getStudentInfo(veriusCookies).orElseThrow(() -> new KnuApiCallFailedException());

        // 세션인포 정보 삽입
        Map<String, Object> sessionInfo = new HashMap<>();
        sessionInfo.put("mobileCookies", mobileCookies);
        sessionInfo.put("ssoCookies", ssoCookies);
        sessionInfo.put("veriusCookies", veriusCookies);

        Map<String, Object> responseList = new HashMap<>();
        responseList.put("sessionInfo", sessionInfo);
        responseList.put("studentInfo", studentInfo);

        return new ResponseEntity<>(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("로그인 성공")
                .list(responseList)
                .build(), HttpStatus.OK);
    }

    /**
     * 세션 갱신 API
     *
     * @param refreshDto
     * @return
     */
    @PostMapping("/knu/refresh/session")
    public ResponseEntity<CommonResponse> knuRefreshSession(@Valid @RequestBody RequestKnu.Refresh refreshDto) {
        //모바일은 유효여부 판단 후 사용하고 나머지는 새로 만든다
        //모바일 로그인
        Map<String, String> mobileCookies = authService.knuMobileRefreshSession(refreshDto.getId(), refreshDto.getPassword(), refreshDto.getSessionInfo()).orElseThrow(() -> new LoginFailedException());
        //통합 SSO 로그인
        Map<String, String> ssoCookies = authService.knuSsoLogin(refreshDto.getId(), refreshDto.getPassword()).orElseThrow(() -> new KnuReadTimeOutException("sso"));
        // 참인재 로그인
        Map<String, String> veriusCookies = authService.refreshVeriusLogin(ssoCookies, refreshDto.getSessionInfo()).orElseThrow(() -> new KnuReadTimeOutException("verius"));


        // 세션인포 정보 삽입
        Map<String, Object> sessionInfo = new HashMap<>();
        sessionInfo.put("mobileCookies", mobileCookies);
        sessionInfo.put("ssoCookies", ssoCookies);
        sessionInfo.put("veriusCookies", veriusCookies);

        Map<String, Object> responseList = new HashMap<>();
        responseList.put("sessionInfo", sessionInfo);

        return new ResponseEntity<>(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("로그인 성공")
                .list(responseList)
                .build(), HttpStatus.OK);
    }

    @PostMapping("/knu/logout")
    public ResponseEntity<CommonResponse> knuLogout(@RequestBody RequestKnu.Logout logoutDto) {

        authService.knuMobileLogout(logoutDto.getSessionInfo().getMobileCookies());

        return new ResponseEntity<>(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("로그아웃 성공")
                .list(null)
                .build(), HttpStatus.OK);
    }

    @PostMapping("/knu/login/staff")
    public ResponseEntity<CommonResponse> knuStaffLogin(@Valid @RequestBody RequestKnu.Login loginDto) {
        //통합 SSO 로그인
        Map<String, String> ssoCookies = authService.knuSsoLogin(loginDto.getId(), loginDto.getPassword()).orElseThrow(()->new LoginFailedException());
        // 참인재 로그인
        Map<String, String> veriusCookies = authService.veriusLogin(ssoCookies).orElseThrow(()->new KnuReadTimeOutException("verius"));

        // 세션인포 정보 삽입
        Map<String, Object> sessionInfo = new HashMap<>();
        sessionInfo.put("ssoCookies", ssoCookies);
        sessionInfo.put("veriusCookies", veriusCookies);

        Map<String, Object> responseList = new HashMap<>();
        responseList.put("sessionInfo", sessionInfo);

        return new ResponseEntity<>(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("로그인 성공")
                .list(responseList)
                .build(), HttpStatus.OK);
    }

}
