package com.allknu.backend.repository;

import com.allknu.backend.domain.MealType;
import com.allknu.backend.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    Menu findByMenuName(String menuName);
    List<Menu> findByMealDate(Date mealDate);
    List<Menu> findByMealType(MealType mealType);

    @Query("select m from Menu m " +
            "join m.restaurant r " +
            "where r.restaurantName = :restaurantName " +
            "and m.mealDate = :date")
    List<Menu> findAllByDateAndRestaurant(@Param("restaurantName") String restaurantName, @Param("date") Date date);

    @Query("select m from Menu m " +
            "join m.restaurant r " +
            "where r.restaurantName = :restaurantName " +
            "and m.mealDate = :date " +
            "and m.mealType = :type")
    List<Menu> findByRestaurantAndDateAndType(@Param("restaurantName") String restaurantName, @Param("date") Date date, @Param("type") MealType type);
    @Query("delete from Menu m " +
            "where m in(:menuList)")
    @Modifying
    void deleteMenuList(@Param("menuList") List<Menu> menuList);
}
