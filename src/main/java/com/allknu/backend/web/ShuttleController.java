package com.allknu.backend.web;

import com.allknu.backend.core.service.ShuttleService;
import com.allknu.backend.web.dto.CommonResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ShuttleController {
    private final ShuttleService shuttleService;

    @PostMapping("/knu/shuttle")
    public ResponseEntity<CommonResponse> addStation(@RequestBody Map<String, String>  station){
        shuttleService.registerStation(station.get("station"));

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("달구지 정거장 등록 성공")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/knu/shuttle")
    public ResponseEntity<CommonResponse> listStation(){
        List<String> stationList = shuttleService.listStation();

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("달구지 정거장 조회 성공")
                .list(stationList)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/knu/shuttle")
    public ResponseEntity<CommonResponse> deleteStation(@RequestBody Map<String, String>  station){
        shuttleService.deleteStation(station.get("station"));

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("달구지 정거장 삭제 성공")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
