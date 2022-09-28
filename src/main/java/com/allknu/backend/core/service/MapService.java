package com.allknu.backend.core.service;

import com.allknu.backend.web.dto.RequestMap;

public interface MapService {
    Long createMarker(RequestMap.CreateMarker createMarkerDto);
}
