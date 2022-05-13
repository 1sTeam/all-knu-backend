package com.allknu.backend.core.service;


import com.allknu.backend.core.types.MealType;
import com.allknu.backend.web.dto.ResponseRestaurant;

import java.util.Date;
import java.util.List;

public interface RestaurantService {
   void registerRestaurant(String restaurant);
   void registerMenu(String restaurant, Date date, List<String> menu, MealType time);
   List<String> getAllRestaurants();
   List<ResponseRestaurant.FindMenu> getAllMenuByDate(Date date);
   void deleteRestaurant(String restaurant);
   void deleteMenu(String restaurant, Date date, MealType time);
}
