package com.allknu.backend.repository;

import com.allknu.backend.core.types.MealType;
import com.allknu.backend.entity.Menu;
import com.allknu.backend.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    Menu findByMenuName(String menuName);
    List<Menu> findByMealDate(Date mealDate);
    Menu findByMealType(MealType mealType);

    @Query("select m from Menu m " +
            "join m.restaurant r " +
            "where r.restaurantName = :restaurantName " +
            "and m.mealDate = :date")
    List<Menu> findAllByDateAndRestaurant(String restaurantName, Date date);

    @Query("select m from Menu m " +
            "join m.restaurant r " +
            "where r.restaurantName = :restaurantName " +
            "and m.mealDate = :date " +
            "and m.mealType = :time")
    List<Menu> findByRestaurantAndDateAndTime(String restaurantName, Date date, MealType time);
}
