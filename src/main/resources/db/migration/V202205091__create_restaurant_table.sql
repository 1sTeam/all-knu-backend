CREATE TABLE restaurant (
    restaurant_name VARCHAR(63) NOT NULL PRIMARY KEY
);

CREATE TABLE menu (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    meal_date DATE,
    meal_type VARCHAR(16),
    menu_name VARCHAR(63),
    restaurant_name VARCHAR(63) NOT NULL,
    FOREIGN KEY (restaurant_name) REFERENCES restaurant (restaurant_name)
);
