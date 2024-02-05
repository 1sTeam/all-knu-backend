package com.allknu.backend.fcmapi.domain.dto;

import com.allknu.backend.fcmapi.domain.SubscribeType;
import com.allknu.backend.fcmapi.domain.AndroidPriority;
import com.allknu.backend.fcmapi.domain.ApnsPriority;
import com.allknu.backend.fcmapi.domain.ApnsPushType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PushToTopicsRequestDto {

    private List<SubscribeType> subscribeTypes; //보내고자하는 구독 유형 리스트
    private String title;
    private String body;
    private String clickLink;

    private ApnsPushType apnsPushType;
    private ApnsPriority apnsPriority;
    private AndroidPriority androidPriority;
}

