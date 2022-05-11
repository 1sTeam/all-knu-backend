package com.allknu.backend.web.dto;

import com.allknu.backend.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ResponseRestaurant {
    @Builder
    @Data
    public static class FindMenu {
        private String name;
        private List<String> breakfast;
        private List<String> lunch;
        private List<String> dinner;
    }
}
