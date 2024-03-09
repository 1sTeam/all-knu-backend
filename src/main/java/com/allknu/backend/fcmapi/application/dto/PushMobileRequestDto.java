package com.allknu.backend.fcmapi.application.dto;

import com.allknu.backend.fcmapi.domain.AndroidPriority;
import com.allknu.backend.fcmapi.domain.ApnsPriority;
import com.allknu.backend.fcmapi.domain.ApnsPushType;
import com.allknu.backend.fcmapi.domain.SubscribeType;
import com.allknu.backend.fcmapi.domain.dto.PushToTopicsRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PushMobileRequestDto {

    private List<SubscribeType> subscribeTypes; //보내고자하는 구독 유형 리스트
    @NotEmpty(message = "제목이 비어있다.")
    private String title;
    @NotEmpty(message = "내용이 비어있다.")
    private String body;
    @NotEmpty(message = "클릭 링크가 비어있다.")
    private String clickLink;
    private ApnsPushType apnsPushType;
    private ApnsPriority apnsPriority;
    private AndroidPriority androidPriority;

    public PushToTopicsRequestDto toPushToTopicsRequestDto() {
        return PushToTopicsRequestDto.builder()
                .subscribeTypes(this.subscribeTypes)
                .title(this.title)
                .body(this.body)
                .clickLink(this.clickLink)
                .apnsPushType(this.apnsPushType)
                .apnsPriority(this.apnsPriority)
                .androidPriority(this.androidPriority)
                .build();
    }
}
