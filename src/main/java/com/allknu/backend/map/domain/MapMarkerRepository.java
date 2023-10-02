package com.allknu.backend.map.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MapMarkerRepository extends JpaRepository<MapMarker, Long> {

    @Query("select m from MapMarker m join fetch m.mapMarkerOperationInfo")
    List<MapMarker> findAllMapMarker();
}
