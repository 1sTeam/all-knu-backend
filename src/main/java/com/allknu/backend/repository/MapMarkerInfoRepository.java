package com.allknu.backend.repository;

import com.allknu.backend.domain.MapMarkerOperationInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MapMarkerInfoRepository extends JpaRepository<MapMarkerOperationInfo, Long> {
}
