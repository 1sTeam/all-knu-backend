package com.allknu.backend.shuttle.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DayRepository extends JpaRepository<Day,String> {
    Day findByName(String day);
}
