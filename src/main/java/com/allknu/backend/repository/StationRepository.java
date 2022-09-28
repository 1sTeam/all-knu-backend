package com.allknu.backend.repository;

import com.allknu.backend.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, String> {
    Station findByStation(String station);
}
