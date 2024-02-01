package com.allknu.backend.restaurant.domain;

import com.allknu.backend.restaurant.domain.MealType;
import com.allknu.backend.restaurant.domain.Menu;
import com.allknu.backend.restaurant.domain.Restaurant;
import com.allknu.backend.restaurant.domain.MenuRepository;
import com.allknu.backend.restaurant.domain.RestaurantRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

                Optional<Restaurant> restaurantAdd = restaurantRepository.findByRestaurantName(restaurant.getRestaurantName());
                assertTrue(restaurantAdd.isPresent());
                System.out.println(restaurantAdd.get().getRestaurantName());
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
