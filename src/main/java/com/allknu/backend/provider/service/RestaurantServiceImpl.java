package com.allknu.backend.provider.service;

import com.allknu.backend.core.service.RestaurantService;
import com.allknu.backend.core.types.MealType;
import com.allknu.backend.entity.Menu;
import com.allknu.backend.entity.Restaurant;
import com.allknu.backend.repository.MenuRepository;
import com.allknu.backend.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;

    @Override
    @Transactional
    public void registerRestaurant(String restaurant){
        // 추후에 이미 식당이 등록되어 있을 경우 예외 처리 필요
        Restaurant res = Restaurant.builder()
                .restaurantName(restaurant)
                .build();
        restaurantRepository.save(res);
    }

    @Override
    @Transactional
    public void registerMenu(String restaurant, Date date, List<String> menu, MealType time){
        //추후에 메뉴가 이미 등록되어 있다면 예외처리 필요
        Restaurant res = restaurantRepository.findByRestaurantName(restaurant);
        for(int i=0;i<menu.size();i++) {
            Menu menu1 = Menu.builder()
                    .menuName(menu.get(i))
                    .mealDate(date)
                    .mealType(time)
                    .restaurant(res)
                    .build();
            menuRepository.save(menu1);
        }
    }
}
