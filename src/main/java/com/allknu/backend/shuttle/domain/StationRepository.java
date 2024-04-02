package com.allknu.backend.shuttle.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, String> {
    Station findByStation(String station);
}
