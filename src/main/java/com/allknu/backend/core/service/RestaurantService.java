package com.allknu.backend.core.service;


import com.allknu.backend.core.types.MealType;

import java.util.Date;
import java.util.List;

public interface RestaurantService {
   void registerRestaurant(String restaurant);
   void registerMenu(String restaurant, Date date, List<String> menu, MealType time);
   List<String> getAllRestaurants();
}
