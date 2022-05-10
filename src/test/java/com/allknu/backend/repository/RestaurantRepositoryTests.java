package com.allknu.backend.repository;

import com.allknu.backend.core.types.MealType;
import com.allknu.backend.entity.Menu;
import com.allknu.backend.entity.Restaurant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class RestaurantRepositoryTests {
        @Autowired
        private RestaurantRepository restaurantRepository;
        @Autowired
        private MenuRepository menuRepository;

        @Transactional
        @DisplayName("식당 추가 테스트")
        @Test
        void addRestaurant(){
                Restaurant restaurant = Restaurant.builder()
                        .restaurantName("식당 샬식당")
                        .build();
                restaurant = restaurantRepository.save(restaurant);

                Restaurant restaurantAdd = restaurantRepository.findByRestaurantName(restaurant.getRestaurantName());
                assertNotNull(restaurantAdd);
                System.out.println(restaurantAdd.getRestaurantName());
        }
        @Transactional
        @DisplayName("메뉴 추가 테스트")
        @Test
        void addMenu(){
                Restaurant restaurant = Restaurant.builder()
                        .restaurantName("식당 샬식당")
                        .build();
                restaurant = restaurantRepository.save(restaurant);

                Date date = new Date();
                Menu menu = Menu.builder()
                        .menuName("감자채 볶음")
                        .mealDate(date)
                        .mealType(MealType.LUNCH)
                        .restaurant(restaurant)
                        .build();
                menu = menuRepository.save(menu);
                assertNotNull(menu);
                System.out.println(menu.getRestaurant()+" "+menu.getMenuName()+" "+menu.getMealDate()+" "+menu.getMealType());
        }


}
