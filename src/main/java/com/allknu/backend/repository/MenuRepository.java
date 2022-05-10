package com.allknu.backend.repository;

import com.allknu.backend.entity.Menu;
import com.allknu.backend.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    Menu findByMenuName(String menuName);
}
