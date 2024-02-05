package com.allknu.backend.fcmapi.domain;

import com.allknu.backend.fcmapi.domain.dto.PushToTopicsRequestDto;
import com.allknu.backend.fcmapi.domain.dto.UpdateSubscribeTopicsRequestDto;

public interface PushRepository {

    void pushToTopicsRequest(PushToTopicsRequestDto requestDto);

    void updateSubscribeTopicsRequest(UpdateSubscribeTopicsRequestDto requestDto);
}
