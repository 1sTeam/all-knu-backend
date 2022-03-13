package com.allknu.backend.web.dto;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Null;
import java.util.Map;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SessionInfo {
    private Map<String, String> mobileCookies;
    private Map<String, String> ssoCookies;
}
