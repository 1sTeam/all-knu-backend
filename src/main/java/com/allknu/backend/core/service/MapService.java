package com.allknu.backend.core.service;

import com.allknu.backend.web.dto.RequestMap;
import com.allknu.backend.web.dto.ResponseMap;

import java.util.List;

public interface MapService {
    Long createMarker(RequestMap.CreateMarker createMarkerDto);
    void deleteMarker(String name);
    List<ResponseMap.GetMapMarker> getMapMarkers();

}
