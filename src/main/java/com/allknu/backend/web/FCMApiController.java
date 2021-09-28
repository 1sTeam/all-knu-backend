package com.allknu.backend.web;

import com.allknu.backend.provider.service.FCMApiService;
import com.allknu.backend.web.dto.CommonResponse;
import com.allknu.backend.web.dto.RequestFCMMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class FCMApiController {
    private final FCMApiService fcmApiService;

    @PostMapping("/dev/fcm/push/web")
    public ResponseEntity<CommonResponse> requestPushNotificationToWeb(@RequestBody @Valid RequestFCMMessage.Web message) {

        fcmApiService.pushToKafkaWebMessage(message);

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
}
