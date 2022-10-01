package com.allknu.backend.web;

import com.allknu.backend.core.service.MapService;
import com.allknu.backend.web.dto.CommonResponse;
import com.allknu.backend.web.dto.RequestMap;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MapController {
    private final MapService mapService;

    @PostMapping("/api/v1/knu/map/marker")
    public ResponseEntity<CommonResponse> addMapMarker(@Valid @RequestBody RequestMap.CreateMarker createMarkerDto) {

        mapService.createMarker(createMarkerDto);

        return new ResponseEntity<>(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("추가 성공")
                .build(), HttpStatus.OK);
    }
    @DeleteMapping("/api/vi/knu/map/marker/{name}")
    public ResponseEntity<CommonResponse> deleteMapMarker(@PathVariable("name") String name) {
        mapService.deleteMarker(name);

        return new ResponseEntity<>(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("지도 마커 삭제 성공")
                .build(), HttpStatus.OK);
    }
}