package com.allknu.backend.web;

import com.allknu.backend.core.service.ShuttleService;
import com.allknu.backend.web.dto.CommonResponse;
import com.allknu.backend.web.dto.RequestStationTimetable;
import com.allknu.backend.web.dto.ResponseStation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
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
        List<String> stationList = shuttleService.getAllStation();

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

    @PostMapping("/knu/shuttle/timetable")
    public ResponseEntity<CommonResponse> addStationTimetable(@RequestBody RequestStationTimetable.addStationTime requestDto){
        shuttleService.registerStationTimetable(requestDto.getStation(), requestDto.getTime());
        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("달구지 시간표 등록 성공")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/knu/shuttle/timetable")
    public ResponseEntity<CommonResponse> getAllStationTimetable(){
        List<ResponseStation.stationTime> stationTimetables = shuttleService.getAllStationTimetable();

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("달구지 정거장 시간표 조회 성공")
                .list(stationTimetables)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
