package com.allknu.backend.restaurant.application;

import com.allknu.backend.restaurant.application.RestaurantService;
import com.allknu.backend.restaurant.domain.MealType;
import com.allknu.backend.restaurant.domain.Menu;
import com.allknu.backend.restaurant.domain.Restaurant;
import com.allknu.backend.global.exception.errors.RestaurantNameDuplicatedException;
import com.allknu.backend.restaurant.domain.MenuRepository;
import com.allknu.backend.restaurant.domain.RestaurantRepository;
import com.allknu.backend.restaurant.application.dto.ResponseRestaurant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    @DisplayName("식당 추가 중복 오류 테스트")
    void registerRestaurantDuplicatedTest() {
        restaurantService.registerRestaurant("샬롬관 학식당");    //식당 저장
        assertThrows(RestaurantNameDuplicatedException.class, () ->   //식당 중복 예외가 발생하면 테스트 성공
            restaurantService.registerRestaurant("샬롬관 학식당"));
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

    @Test
    @Transactional
    @DisplayName("식당 조회 서비스 테스트")
    void getAllRestaurantsTest(){
        Restaurant res = Restaurant.builder()
                .restaurantName("샬롬관 학식당")
                .build();
        restaurantRepository.save(res);
        res = Restaurant.builder()
                .restaurantName("경천관 학식당")
                .build();
        restaurantRepository.save(res);
        List<String> list = restaurantService.getAllRestaurants();
        assertEquals(list.size(),2);
        System.out.println(list);
    }

    @Test
    @Transactional
    @DisplayName("메뉴 조회 서비스 테스트")
    void getAllMenuByDateTest(){
        Date date = new Date();
        List<String> list = new ArrayList<>();
        list.add("감자채 볶음");
        list.add("꿀떡");
        list.add("요구르트");
        Restaurant res = Restaurant.builder()
                .restaurantName("샬롬관 학식당")
                .build();
        restaurantRepository.save(res);
        restaurantService.registerMenu("샬롬관 학식당", date, list, MealType.LUNCH);
        List<ResponseRestaurant.FindMenu> list1=restaurantService.getAllMenuByDate(date);
        assertEquals(list1.get(0).getLunch().size(), 3);
        for(int i=0;list1.size() > i;i++){      //날짜별 메뉴 모두 출력
            System.out.println(list1.get(i).getName()); //해당 학식당 이름 출력
            System.out.println("런치" + list1.get(i).getLunch()); //저장한 런치의 메뉴 리스트 출력
        }
    }
    @Test
    @Transactional
    @DisplayName("식당 삭제 서비스 테스트")
    void deleteRestaurantTest(){
        List<String> list = new ArrayList<>();
        list.add("감자채 볶음");
        list.add("꿀떡");
        list.add("요구르트");
        Date date = new Date();
        //식당 등록
        Restaurant res = Restaurant.builder()
                .restaurantName("샬롬관 학식당")
                .build();
        restaurantRepository.save(res);
        Restaurant res1 = Restaurant.builder()
                .restaurantName("샬롬관")
                .build();
        restaurantRepository.save(res);
        restaurantRepository.save(res1);
        //메뉴 등록
        restaurantService.registerMenu("샬롬관 학식당", date, list, MealType.LUNCH);
        //식당 삭제
        restaurantService.deleteRestaurant("샬롬관 학식당");
        assertEquals(menuRepository.findByMealDate(date).size(),0);
    }
    @Test
    @Transactional
    @DisplayName("메뉴 삭제 서비스 테스트")
    void deleteMenuTest(){
        List<String> list = List.of("감자채 볶음", "꿀떡", "요구르트");
        Date date = new Date();
        //식당 등록
        Restaurant res = Restaurant.builder()
                .restaurantName("샬롬관 학식당")
                .build();
        restaurantRepository.save(res);
        //메뉴 등록
        //식당 등록
        restaurantService.registerMenu("샬롬관 학식당", date, list, MealType.LUNCH);
        restaurantService.registerMenu("샬롬관 학식당", date, list, MealType.DINNER);
        //식당 삭제
        restaurantService.deleteMenu("샬롬관 학식당",date,MealType.LUNCH);
        List<Menu> menuList = menuRepository.findByMealType(MealType.LUNCH);
        List<Menu> menuList1 = menuRepository.findByMealType(MealType.DINNER);
        assertEquals(menuList.size(), 0);
        assertEquals(menuList1.size(), 3);

    }

}
