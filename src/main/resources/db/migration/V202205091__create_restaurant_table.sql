CREATE TABLE restaurant (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    restaurant_name VARCHAR(63)
);

CREATE TABLE menu (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    meal_date DATE,
    meal_type VARCHAR(16),
    menu_name VARCHAR(63),
    restaurant_id BIGINT NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id)
);
