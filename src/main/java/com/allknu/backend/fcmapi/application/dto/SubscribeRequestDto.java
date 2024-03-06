package com.allknu.backend.fcmapi.application.dto;

import com.allknu.backend.fcmapi.domain.SubscribeType;
import com.allknu.backend.fcmapi.domain.dto.UpdateSubscribeTopicsRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscribeRequestDto {

    @NotEmpty(message = "토큰이 비어있습니다")
    private String token; // 토큰
    @NotNull(message = "구독 유형이 없습니다.")
    private List<SubscribeType> subscribeTypes;

    public UpdateSubscribeTopicsRequestDto toUpdateSubscribeTopicsRequestDto() {
        return UpdateSubscribeTopicsRequestDto.builder()
                .token(this.token)
                .subscribes(this.subscribeTypes)
                .build();
    }
}
