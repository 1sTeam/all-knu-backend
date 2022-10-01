package com.allknu.backend.core.service;

import com.allknu.backend.web.dto.RequestMap;
import com.allknu.backend.web.dto.ResponseMap;

import java.util.List;

public interface MapService {
    Long createMarker(RequestMap.CreateMarker createMarkerDto);
    void deleteMarker(Long id);
    List<ResponseMap.GetMapMarker> getMapMarkers();

}
