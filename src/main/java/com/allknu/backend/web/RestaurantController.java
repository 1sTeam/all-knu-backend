package com.allknu.backend.web;

import com.allknu.backend.core.service.KnuMobileApiService;
import com.allknu.backend.core.types.MealType;
import com.allknu.backend.web.dto.CommonResponse;
import com.allknu.backend.web.dto.RequestKnu;
import com.allknu.backend.web.dto.RequestRestaurant;
import com.allknu.backend.core.service.RestaurantService;
import com.allknu.backend.web.dto.ResponseRestaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

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

        /*
        * 모바일 앱 1.5버전에서 파라미터로 date를 잘 넘겨주지 못하는 이슈가 있어 임시로 API 호출 시 무조건 오늘 날짜의 식단표를 반환하도록 한다.
        * 모바일 앱 이용자들이 이 문제를 개선한 버전의 앱을 충분히 설치했다고 인지되는 시점에 다시 파라미터의 date를 조회하도록 하게 한다.
        * */
        System.out.println(date);
        List<ResponseRestaurant.FindMenu> info = restaurantService.getAllMenuByDate(new Date()); // 임시로 무조건 오늘날짜
        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("식단표 조회 성공")
                .list(info)
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
