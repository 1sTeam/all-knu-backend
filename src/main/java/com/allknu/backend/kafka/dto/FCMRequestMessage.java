package com.allknu.backend.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FCMRequestMessage {
    String sendType;
    String target;
    String title;
    String body;
    String clickLink;
}
