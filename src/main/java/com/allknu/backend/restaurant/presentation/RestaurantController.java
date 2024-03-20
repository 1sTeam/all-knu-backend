package com.allknu.backend.restaurant.presentation;

import com.allknu.backend.restaurant.domain.MealType;
import com.allknu.backend.global.dto.CommonResponse;
import com.allknu.backend.restaurant.application.dto.RequestRestaurant;
import com.allknu.backend.restaurant.application.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Date;

@RestController
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;

    @PostMapping("/knu/restaurant")
    public ResponseEntity<CommonResponse> RegisterRestaurant(@Valid @RequestBody RequestRestaurant.RegisterRestaurant restaurantDto) {

        restaurantService.registerRestaurant(restaurantDto.getRestaurant());

        return new ResponseEntity<>(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("식당 추가 성공")
                .build(), HttpStatus.OK);
    }
    @GetMapping("/knu/restaurant")
    public ResponseEntity<CommonResponse> getAllRestaurants() {


        return new ResponseEntity<>(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("식당 조회 성공")
                .list(restaurantService.getAllRestaurants())
                .build(), HttpStatus.OK);
    }
    @DeleteMapping("/knu/restaurant/{restaurant}")
    public ResponseEntity<CommonResponse> deleteRestaurant(@PathVariable("restaurant") String restaurant){

        restaurantService.deleteRestaurant(restaurant);

        return new ResponseEntity<>(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("식당 삭제 성공")
                .build(), HttpStatus.OK);
    }
    @PostMapping("/knu/restaurant/menu")
    public ResponseEntity<CommonResponse> RegisterMenu(@Valid @RequestBody RequestRestaurant.RegisterMenu menuDto){

        restaurantService.registerMenu(menuDto.getRestaurant(), menuDto.getDate(), menuDto.getMenu(), menuDto.getTime());

        return new ResponseEntity<>(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("식단표 추가 성공")
                .build(), HttpStatus.OK);
    }
    @GetMapping("/knu/restaurant/menu")
    public ResponseEntity<CommonResponse> findMenu(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date){

        if(date == null) date = new Date();

        return new ResponseEntity<>(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("식단표 조회 성공")
                .list(restaurantService.getAllMenuByDate(date))
                .build(), HttpStatus.OK);
    }
    @DeleteMapping("/knu/restaurant/menu/{restaurant}/{date}/{time}")
    public ResponseEntity<CommonResponse> deleteMenu(@PathVariable("restaurant") String restaurant, @PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, @PathVariable("time") MealType type){

        restaurantService.deleteMenu(restaurant, date, type);

        return new ResponseEntity<>(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("식단표 삭제 성공")
                .build(), HttpStatus.OK);
    }
}
