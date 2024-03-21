package com.allknu.backend.shuttle.presentation;

import com.allknu.backend.shuttle.application.ShuttleService;
import com.allknu.backend.global.dto.CommonResponse;
import com.allknu.backend.shuttle.application.dto.RequestStationTimetable;
import com.allknu.backend.shuttle.application.dto.ResponseStation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ShuttleController {
    private final ShuttleService shuttleService;

    @PostMapping("/knu/shuttle")
    public ResponseEntity<CommonResponse> addStation(@RequestBody Map<String, String>  station){
        shuttleService.registerStation(station.get("station"));

        return new ResponseEntity<>(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("달구지 정거장 등록 성공")
                .build(), HttpStatus.OK);
    }

    @GetMapping("/knu/shuttle")
    public ResponseEntity<CommonResponse> listStation(){
        List<String> stationList = shuttleService.getAllStation();

        return new ResponseEntity<>(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("달구지 정거장 조회 성공")
                .list(stationList)
                .build(), HttpStatus.OK);
    }
    @DeleteMapping("/knu/shuttle/{station}")
    public ResponseEntity<CommonResponse> deleteStation(@PathVariable String station){
        shuttleService.deleteStation(station);

        return new ResponseEntity<>(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("달구지 정거장 삭제 성공")
                .build(), HttpStatus.OK);
    }

    @PostMapping("/api/v2/knu/shuttle/timetable")
    public ResponseEntity<CommonResponse> registerStationTimetable(@RequestBody RequestStationTimetable.registerStationTime requestDto) {
        shuttleService.registerStationTimetable(requestDto.getStation(), requestDto.getDay(), requestDto.getTime(), requestDto.getDestination());

        return new ResponseEntity<>(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("달구지 시간표 등록 성공")
                .build(), HttpStatus.OK);
    }

    @GetMapping("/api/v2/knu/shuttle/timetable")
    public ResponseEntity<CommonResponse> getStationTimetable(@RequestParam String day) {
        List<ResponseStation.getStationTime> stationTimetables = shuttleService.getStationTimetable(day);

        return new ResponseEntity<>(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("달구지 정거장 시간표 조회 성공")
                .list(stationTimetables)
                .build(), HttpStatus.OK);
    }
    @DeleteMapping("/api/v2/knu/shuttle/{station}/{day}/{time}")
    public ResponseEntity<CommonResponse> deleteStationTimetable(@PathVariable String station,
                                                                 @PathVariable String day,
                                                                 @PathVariable @DateTimeFormat(pattern = "HH:mm:ss") Date time) {
        shuttleService.deleteStationTimetable(station, day, time);

        return new ResponseEntity<>(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("달구지 정거장 시간표 삭제 성공")
                .build(), HttpStatus.OK);
    }

}
