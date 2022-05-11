package com.allknu.backend.provider.service;

import com.allknu.backend.core.service.RestaurantService;
import com.allknu.backend.core.types.MealType;
import com.allknu.backend.entity.Menu;
import com.allknu.backend.entity.Restaurant;
import com.allknu.backend.exception.errors.NotFoundMenuException;
import com.allknu.backend.exception.errors.NotFoundRestaurantException;
import com.allknu.backend.exception.errors.RestaurantNameDuplicatedException;
import com.allknu.backend.repository.MenuRepository;
import com.allknu.backend.repository.RestaurantRepository;
import com.allknu.backend.web.dto.ResponseRestaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        if(restaurantRepository.findByRestaurantName(restaurant)!=null) {
            throw new RestaurantNameDuplicatedException();
        }
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
            res.addMenu(menu1);
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
        Restaurant res = restaurantRepository.findByRestaurantName(restaurant);
        if(res == null){
            throw new NotFoundRestaurantException();
        }
        restaurantRepository.delete(res);
    }

    @Override
    @Transactional
    public void deleteMenu(String restaurant, Date date, MealType time) {
        List<Menu> menuList = menuRepository.findByRestaurantAndDateAndTime(restaurant, date, time);
        if(menuList == null){
            System.out.println("메뉴 없음");
            throw new NotFoundMenuException();
        }
        for(int i = 0 ; i < menuList.size() ; i++) {
            Menu menu = menuList.get(i);
            menuRepository.delete(menu);
        }
        if(menuRepository.findAll()==null){
            System.out.println("다 지워짐");
        }
    }
}
