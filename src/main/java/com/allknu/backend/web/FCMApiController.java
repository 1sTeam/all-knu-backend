package com.allknu.backend.web;

import com.allknu.backend.entity.FirebaseLog;
import com.allknu.backend.exception.errors.CustomJwtRuntimeException;
import com.allknu.backend.kafka.dto.FCMMobileMessage;
import com.allknu.backend.kafka.dto.FCMWebMessage;
import com.allknu.backend.provider.security.JwtAuthToken;
import com.allknu.backend.provider.security.JwtAuthTokenProvider;
import com.allknu.backend.provider.service.FCMApiService;
import com.allknu.backend.web.dto.CommonResponse;
import com.allknu.backend.web.dto.RequestFCMMessage;
import com.allknu.backend.web.dto.ResponseFcm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class FCMApiController {
    private final FCMApiService fcmApiService;
    private final JwtAuthTokenProvider jwtAuthTokenProvider;

    @Deprecated
    @PostMapping("/fcm/push/web")
    public ResponseEntity<CommonResponse> requestPushNotificationToWeb(@RequestBody @Valid RequestFCMMessage.Web message, HttpServletRequest request) {
        // 나중에 이 검증을 관리자 마이크로서비스에서 처리하도록 개선해야함, web push 포함
        //토큰에서 이메일 꺼내기
        Optional<String> token = jwtAuthTokenProvider.resolveToken(request);
        String email = null;
        // 나중에 개선필요
        if(token.isPresent()) {
            JwtAuthToken jwtAuthToken = jwtAuthTokenProvider.convertAuthToken(token.get());
            jwtAuthToken.validate();
            email = jwtAuthToken.getData().getSubject();
        } else throw new CustomJwtRuntimeException();

        FCMWebMessage fcmWebMessage = message.toFCMWebMessage();
        fcmApiService.pushToKafkaWebMessage(email, fcmWebMessage);

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("fcm 마이크로서비스에 웹 푸쉬 알림 요청 성공")
                .list(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/fcm/push/mobile")
    public ResponseEntity<CommonResponse> requestPushNotificationToMobile(@RequestBody @Valid RequestFCMMessage.Mobile message, HttpServletRequest request) {
        // 나중에 이 검증을 관리자 마이크로서비스에서 처리하도록 개선해야함, web push 포함
        //토큰에서 이메일 꺼내기
        Optional<String> token = jwtAuthTokenProvider.resolveToken(request);
        String email = null;
        // 나중에 개선필요
        if(token.isPresent()) {
            JwtAuthToken jwtAuthToken = jwtAuthTokenProvider.convertAuthToken(token.get());
            jwtAuthToken.validate();
            email = jwtAuthToken.getData().getSubject();
        } else throw new CustomJwtRuntimeException();

        FCMMobileMessage fcmMobileMessage = message.toFCMMobileMessage();
        fcmApiService.pushToKafkaMobileMessage(email, fcmMobileMessage);

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("fcm 마이크로서비스에 mobile 푸쉬 알림 요청 성공")
                .list(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/dev/fcm/subscribe")
    public ResponseEntity<CommonResponse> requestSubscribe(@RequestBody @Valid RequestFCMMessage.Subscribe message) {

        fcmApiService.pushToKafkaSubscribeMessage(message);

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("fcm 마이크로서비스에 구독 요청 성공")
                .list(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/fcm/log")
    public ResponseEntity<CommonResponse> getFcmLog(@PageableDefault Pageable pageable) {
        //admin 인터셉터 통과 상태, 전체 로그를 불러온다. 10개 아이템 단위로
        Page<ResponseFcm.Log> logs = fcmApiService.getAllFcmLog(pageable).orElseGet(()->null);

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("fcm 로그 조회 요청 성공")
                .list(logs)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping(value = {"/knu/subscribe/{team}", "/knu/subscribe"})
    public ResponseEntity<CommonResponse> getSubscribeTypes(@PathVariable(value = "team", required = false) String team) {

        List<ResponseFcm.SubscribeType> list = fcmApiService.getAllKnuSubscribeTypes(team);

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("구독 토픽 조회 성공")
                .list(list)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
