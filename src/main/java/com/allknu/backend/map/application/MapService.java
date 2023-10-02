package com.allknu.backend.map.application;

import com.allknu.backend.map.application.dto.RequestMap;
import com.allknu.backend.map.application.dto.ResponseMap;

import java.util.List;

public interface MapService {
    Long createMarker(RequestMap.CreateMarker createMarkerDto);
    void deleteMarker(Long id);
    List<ResponseMap.GetMapMarker> getMapMarkers();

}
