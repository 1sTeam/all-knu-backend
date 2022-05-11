package com.allknu.backend.repository;

import com.allknu.backend.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface StationRepository extends JpaRepository<Station, String> {
    Station findByStation(String station);
}
