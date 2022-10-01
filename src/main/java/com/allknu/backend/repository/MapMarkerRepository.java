package com.allknu.backend.repository;

import com.allknu.backend.domain.MapMarker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MapMarkerRepository extends JpaRepository<MapMarker, Long> {
    MapMarker findByName(String name);
}
