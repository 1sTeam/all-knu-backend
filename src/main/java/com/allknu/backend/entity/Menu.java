package com.allknu.backend.entity;

import com.allknu.backend.core.types.MealType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Table(name="menu")
@Entity
@Getter
@NoArgsConstructor
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column(name = "meal_date")
    @Temporal(TemporalType.DATE)
    private Date mealDate;

    @Column(name = "meal_type")
    @Enumerated(value = EnumType.STRING)
    private MealType mealType;

    @Column(name = "menu_name")
    private String menuName;

    @Builder
    public Menu(Restaurant restaurant,MealType mealType , Date mealDate, String menuName) {
        this.restaurant = restaurant;
        this.mealDate = mealDate;
        this.mealType = mealType;
        this.menuName = menuName;
    }
}