package com.allknu.backend.fcmapi.domain.dto;

import com.allknu.backend.fcmapi.domain.SubscribeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MLRequestMessage {
    private List<SubscribeType> subscribeTypes; //보내고자하는 구독 유형 리스트
    private Integer predict;
    private String title;
    //바디가 없네..?
    private String clickLink;
    private String time;
}
