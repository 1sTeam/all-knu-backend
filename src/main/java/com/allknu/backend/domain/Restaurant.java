package com.allknu.backend.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name="restaurant")
@Entity
@Getter
@NoArgsConstructor
public class Restaurant {
    @Id
    @Column(name = "restaurant_name")
    private String restaurantName;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> menuList = new ArrayList<>();

    @Builder
    public Restaurant(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public void addMenu(Menu menu){
        this.menuList.add(menu);
    }


}
