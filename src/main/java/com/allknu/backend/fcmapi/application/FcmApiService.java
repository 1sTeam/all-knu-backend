package com.allknu.backend.fcmapi.application;

import com.allknu.backend.fcmapi.application.dto.PushMobileRequestDto;
import com.allknu.backend.fcmapi.application.dto.SubscribeRequestDto;
import com.allknu.backend.fcmapi.application.dto.ResponseFcm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FcmApiService {

    void pushMobileMessage(String email, PushMobileRequestDto requestDto);
    void pushSubscribeMessage(SubscribeRequestDto requestDto);
    Page<ResponseFcm.Log> getAllFcmLog(Pageable pageable);
    List<ResponseFcm.SubscribeType> getAllKnuSubscribeTypes(String team);
}
