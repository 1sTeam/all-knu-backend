package com.allknu.backend.knuapi.application.dto;

import lombok.*;

import java.util.Map;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SessionInfo {
    private Map<String, String> mobileCookies;
    private Map<String, String> ssoCookies;
    private Map<String, String> veriusCookies;
}
