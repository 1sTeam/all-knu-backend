package com.allknu.backend.web;

import com.allknu.backend.core.service.KnuMobileApiService;
import com.allknu.backend.web.dto.CommonResponse;
import com.allknu.backend.web.dto.RequestKnu;
import com.allknu.backend.web.dto.RequestRestaurant;
import com.allknu.backend.core.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;

    @PostMapping("/knu/restaurant")
    public ResponseEntity<CommonResponse> RegisterRestaurant(@RequestBody RequestRestaurant.RegisterRestaurant restaurantDto) {

        restaurantService.registerRestaurant(restaurantDto.getRestaurant());

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("식당 추가 성공")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/knu/restaurant/menu")
    public ResponseEntity<CommonResponse> RegisterMenu(@RequestBody RequestRestaurant.RegisterMenu menuDto){

        restaurantService.registerMenu(menuDto.getRestaurant(), menuDto.getDate(), menuDto.getMenu(), menuDto.getTime());

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("식단표 추가 성공")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
