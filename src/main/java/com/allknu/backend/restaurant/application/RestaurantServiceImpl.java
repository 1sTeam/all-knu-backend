package com.allknu.backend.restaurant.application;

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

import java.util.ArrayList;
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

    /**
     * restaurantName으로 안 찾아질 경우 RestaurantNameDuplicatedException(), 403 반환
     * 추후에 이미 등록되어있는 경우 예외 처리도 추가해야 함
     *
     * @param restaurant
     * @author kymokim
     */
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

    /**
     * 마찬가지로 이미 등록된 메뉴에 대한 예외처리 필요
     * menu의 개수만큼 for문을 돌려 menu 빌드, 저장, 식당에 추가 진행
     *
     * @param restaurant
     * @param date
     * @param menu
     * @param time
     * @author kymokim
     */
    @Override
    @Transactional
    public void registerMenu(String restaurant, Date date, List<String> menu, MealType time){
        //추후에 메뉴가 이미 등록되어 있다면 예외처리 필요

        Restaurant res = restaurantRepository.findByRestaurantName(restaurant);
        for(int i=0;i<menu.size();i++) {
            Menu menuItem = Menu.builder()
                    .menuName(menu.get(i))
                    .mealDate(date)
                    .mealType(time)
                    .restaurant(res)
                    .build();
            menuRepository.save(menuItem);
            res.addMenu(menuItem);
        }
    }

    /**
     * repository에서 전체 가져온 후 이름만 뽑아 listDto에 저장 후 반환
     *
     * @return List<String> listDto
     * @author kymokim
     */
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

    /**
     * 식당 전체를 가져와 restaurants에 저장, 아침/점심/저녁을 각각 리스트에 저장 후 itemBuilder로 build하여 반환
     *
     * @param date
     * @return List<ResponseRestaurant.FindMenu> list
     * @author kymokim
     */
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

    /**
     * 식당이름으로 검색 후 없으면 NotFoundRestaurantException(), 404 반환
     * 있으면 식당 삭제
     *
     * @param restaurant
     * @author kymokim
     */
    @Transactional
    @Override
    public void deleteRestaurant(String restaurant) {
        Restaurant res = restaurantRepository.findByRestaurantName(restaurant);
        if(res == null){
            throw new NotFoundRestaurantException();
        }
        restaurantRepository.delete(res);
    }

    /**
     * restaurant, date, type으로 메뉴 찾은 후, 없으면 NotFoundMenuException(), 404 반환
     * 있으면 메뉴리스트 삭제
     *
     * @param restaurant
     * @param date
     * @param type
     * @author kymokim
     */
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
