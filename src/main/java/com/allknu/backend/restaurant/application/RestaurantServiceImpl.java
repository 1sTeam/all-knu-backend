package com.allknu.backend.restaurant.application;

import com.allknu.backend.global.exception.errors.InvalidRestaurantNameException;
import com.allknu.backend.restaurant.domain.MealType;
import com.allknu.backend.restaurant.domain.Menu;
import com.allknu.backend.restaurant.domain.Restaurant;
import com.allknu.backend.global.exception.errors.NotFoundMenuException;
import com.allknu.backend.global.exception.errors.NotFoundRestaurantException;
import com.allknu.backend.global.exception.errors.RestaurantNameDuplicatedException;
import com.allknu.backend.restaurant.domain.MenuRepository;
import com.allknu.backend.restaurant.domain.RestaurantRepository;
import com.allknu.backend.restaurant.application.dto.ResponseRestaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Date;
import java.util.List;


/*
 * 관리자페이지 기능 -> 마이크로서비스 분리 대상
 */
@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;

    @Override
    @Transactional
    public void registerRestaurant(String restaurant){
        // 식당 이름이 공백인지 검증
        if(!StringUtils.hasText(restaurant)) {
            throw new InvalidRestaurantNameException();
        }

        // 이미 식당이 등록되어 있을 경우 예외 처리
        restaurantRepository.findByRestaurantName(restaurant)
                .ifPresent(r -> {
                    throw new RestaurantNameDuplicatedException();
                });

        Restaurant res = Restaurant.builder()
                .restaurantName(restaurant)
                .build();
        restaurantRepository.save(res);
    }


    @Override
    @Transactional
    public void registerMenu(String restaurant, Date date, List<String> menu, MealType time){
        //추후에 메뉴가 이미 등록되어 있다면 예외처리 필요

        Optional<Restaurant> res = restaurantRepository.findByRestaurantName(restaurant);
        if(!res.isPresent()){
            throw new NotFoundRestaurantException();
        }
        for(int i=0;i<menu.size();i++) {
            Menu menuItem = Menu.builder()
                    .menuName(menu.get(i))
                    .mealDate(date)
                    .mealType(time)
                    .restaurant(res.get())
                    .build();
            menuRepository.save(menuItem);
            res.get().addMenu(menuItem);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<String> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        List<String> listDto = new ArrayList<>();
        for(int i = 0 ; i < restaurants.size() ; i++) {
            listDto.add(restaurants.get(i).getRestaurantName());
        }
        return listDto;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ResponseRestaurant.FindMenu> getAllMenuByDate(Date date) {
        List<ResponseRestaurant.FindMenu> list = new ArrayList<>();
        List<Restaurant> restaurants = restaurantRepository.findAll();
        for(int i = 0 ; i < restaurants.size() ; i++) {
            List<String> breakfastList = new ArrayList<>();
            List<String> lunchList = new ArrayList<>();
            List<String> dinnerList = new ArrayList<>();
            ResponseRestaurant.FindMenu.FindMenuBuilder itemBuilder = ResponseRestaurant.FindMenu.builder();

            itemBuilder.name(restaurants.get(i).getRestaurantName());
            List<Menu> menuList = menuRepository.findAllByDateAndRestaurant(restaurants.get(i).getRestaurantName(), date);
            for(int j = 0 ; j < menuList.size() ; j++) {
                Menu menu = menuList.get(j);

                if(menu.getMealType() == MealType.BREAKFAST) {
                    breakfastList.add(menu.getMenuName());
                } else if(menu.getMealType() == MealType.LUNCH) {
                    lunchList.add(menu.getMenuName());
                } else {
                    dinnerList.add(menu.getMenuName());
                }
            }
            itemBuilder.breakfast(breakfastList);
            itemBuilder.lunch(lunchList);
            itemBuilder.dinner(dinnerList);

            list.add(itemBuilder.build());
        }
        return list;
    }

    @Transactional
    @Override
    public void deleteRestaurant(String restaurant) {
        Optional<Restaurant> res = restaurantRepository.findByRestaurantName(restaurant);
        if(!res.isPresent()){
            throw new NotFoundRestaurantException();
        }
        restaurantRepository.delete(res.get());
    }

    @Override
    @Transactional
    public void deleteMenu(String restaurant, Date date, MealType type) {
        List<Menu> menuList = menuRepository.findByRestaurantAndDateAndType(restaurant, date, type);
        if(menuList.size() == 0){
            throw new NotFoundMenuException();
        }
        menuRepository.deleteMenuList(menuList);

    }
}
