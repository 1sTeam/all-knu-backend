package com.allknu.backend.repository;

import com.allknu.backend.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Restaurant findByRestaurantName(String restaurantName);
}