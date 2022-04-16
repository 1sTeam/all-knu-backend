package com.allknu.backend.kafka.dto;

import com.allknu.backend.core.types.SubscribeType;
import com.allknu.backend.core.types.firebase.AndroidPriority;
import com.allknu.backend.core.types.firebase.ApnsPriority;
import com.allknu.backend.core.types.firebase.ApnsPushType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FCMMobileMessage {
    private List<SubscribeType> subscribeTypes; //보내고자하는 구독 유형 리스트
    private String title;
    private String body;
    private String clickLink;

    private ApnsPushType apnsPushType;
    private ApnsPriority apnsPriority;
    private AndroidPriority androidPriority;
}

