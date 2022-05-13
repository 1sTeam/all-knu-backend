package com.allknu.backend.web;

import com.allknu.backend.core.service.KnuMobileApiService;
import com.allknu.backend.core.types.MealType;
import com.allknu.backend.web.dto.CommonResponse;
import com.allknu.backend.web.dto.RequestKnu;
import com.allknu.backend.web.dto.RequestRestaurant;
import com.allknu.backend.core.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;

    @PostMapping("/knu/restaurant")
    public ResponseEntity<CommonResponse> RegisterRestaurant(@Valid @RequestBody RequestRestaurant.RegisterRestaurant restaurantDto) {

        restaurantService.registerRestaurant(restaurantDto.getRestaurant());

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("식당 추가 성공")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/knu/restaurant")
    public ResponseEntity<CommonResponse> getAllRestaurants() {


        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("식당 조회 성공")
                .list(restaurantService.getAllRestaurants())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/knu/restaurant/{restaurant}")
    public ResponseEntity<CommonResponse> deleteRestaurant(@PathVariable("restaurant") String restaurant){

        restaurantService.deleteRestaurant(restaurant);

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("식당 삭제 성공")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/knu/restaurant/menu")
    public ResponseEntity<CommonResponse> RegisterMenu(@Valid @RequestBody RequestRestaurant.RegisterMenu menuDto){

        restaurantService.registerMenu(menuDto.getRestaurant(), menuDto.getDate(), menuDto.getMenu(), menuDto.getTime());

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("식단표 추가 성공")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/knu/restaurant/menu")
    public ResponseEntity<CommonResponse> findMenu(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("식단표 조회 성공")
                .list(restaurantService.getAllMenuByDate(date))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/knu/restaurant/menu/{restaurant}/{date}/{time}")
    public ResponseEntity<CommonResponse> deleteMenu(@PathVariable("restaurant") String restaurant, @PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, @PathVariable("time") MealType type){

        restaurantService.deleteMenu(restaurant, date, type);

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("식단표 삭제 성공")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
