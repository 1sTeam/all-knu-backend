package com.allknu.backend.map.presentation;

import com.allknu.backend.map.application.MapService;
import com.allknu.backend.global.dto.CommonResponse;
import com.allknu.backend.map.application.dto.RequestMap;
import com.allknu.backend.map.application.dto.ResponseMap;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

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
    @DeleteMapping("/api/v1/knu/map/marker/{id}")
    public ResponseEntity<CommonResponse> deleteMapMarker(@PathVariable("id") Long id){
            mapService.deleteMarker(id);

            return new ResponseEntity<>(CommonResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("지도 마커 삭제 성공")
                    .build(), HttpStatus.OK);
    }

    @GetMapping("/api/v1/knu/map/markers")
    public ResponseEntity<CommonResponse> getMapMarkers(){
        List<ResponseMap.GetMapMarker> response = mapService.getMapMarkers();
        return new ResponseEntity<>(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("지도 마커 조회 성공")
                .list(response)
                .build(), HttpStatus.OK);

    }
}
