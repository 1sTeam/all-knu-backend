package com.allknu.backend.web;

import com.allknu.backend.entity.FirebaseLog;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class FCMApiController {
    private final FCMApiService fcmApiService;
    private final JwtAuthTokenProvider jwtAuthTokenProvider;

    @PostMapping("/fcm/push/web")
    public ResponseEntity<CommonResponse> requestPushNotificationToWeb(@RequestBody @Valid RequestFCMMessage.Web message, HttpServletRequest request) {
        //토큰에서 이메일 꺼내기
        Optional<String> token = jwtAuthTokenProvider.resolveToken(request);
        String email = null;
        if(token.isPresent()) {
            JwtAuthToken jwtAuthToken = jwtAuthTokenProvider.convertAuthToken(token.get());
            email = jwtAuthToken.getData().getSubject();
        }

        FCMWebMessage fcmWebMessage = message.toFCMRequestMessage();
        fcmApiService.pushToKafkaWebMessage(email, fcmWebMessage);

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("fcm 마이크로서비스에 웹 푸쉬 알림 요청 성공")
                .list(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/dev/fcm/subscribe")
    public ResponseEntity<CommonResponse> requestSubscribe(@RequestBody @Valid RequestFCMMessage.Subscribe message) {

        if(message.getSubscribeTypes() == null || message.getSubscribeTypes().size() <= 0) {
            CommonResponse response = CommonResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("요청은 성공, 구독은 하지 않는 것으로 확인")
                    .list(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
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
}
