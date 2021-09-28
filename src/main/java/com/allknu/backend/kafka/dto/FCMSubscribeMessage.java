package com.allknu.backend.kafka.dto;

import com.allknu.backend.core.types.SubscribeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FCMSubscribeMessage {
    private String token;
    private List<SubscribeType> subscribes;
}
