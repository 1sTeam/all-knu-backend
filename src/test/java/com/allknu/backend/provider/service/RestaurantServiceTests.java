package com.allknu.backend.provider.service;

import com.allknu.backend.core.service.RestaurantService;
import com.allknu.backend.core.types.MealType;
import com.allknu.backend.entity.Menu;
import com.allknu.backend.entity.Restaurant;
import com.allknu.backend.exception.errors.LoginFailedException;
import com.allknu.backend.repository.MenuRepository;
import com.allknu.backend.repository.RestaurantRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class RestaurantServiceTests {
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Test
    @Transactional
    @DisplayName("식당 추가 서비스 테스트")
    void registerRestaurantTest() {
        restaurantService.registerRestaurant("샬롬관 학식당");    //식당 저장
        Restaurant res = restaurantRepository.findByRestaurantName("샬롬관 학식당");
        assertNotNull(res);
        System.out.println(res.getRestaurantName());
        }

    @Test
    @Transactional
    @DisplayName("메뉴 추가 서비스 테스트")
    void registerMenuTest(){
        List<String> list = new ArrayList<>();
        list.add("감자채 볶음");
        list.add("꿀떡");
        list.add("요구르트");
        Date date = new Date();
        Restaurant res = Restaurant.builder()
                .restaurantName("샬롬관 학식당")
                .build();
        restaurantRepository.save(res);
        restaurantService.registerMenu("샬롬관 학식당", date, list, MealType.LUNCH);
        Menu menu = menuRepository.findByMenuName("감자채 볶음");
        assertNotNull(menu);
        System.out.println(menu.getRestaurant()+" "+menu.getMenuName()+" "+menu.getMealType()+" "+menu.getMealDate());
    }

}
