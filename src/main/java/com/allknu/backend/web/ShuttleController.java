package com.allknu.backend.web;

import com.allknu.backend.web.dto.CommonResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ShuttleController {

    @GetMapping("/knu/shuttle")
    public ResponseEntity<CommonResponse> getAllStation(){

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("달구지 조회 성공")
                .list(List.of("이공관","기흥역"))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/knu/shuttle/timetable")
    public ResponseEntity<CommonResponse> getAllShuttleTimetable(){
        Map<String, Object> t1 = new HashMap<>();
        t1.put("station","기흥역");
        t1.put("stopTime",List.of("08:10:00","08:20:00","08:25:00","08:30:00","08:35:00","08:40:00","08:45:00","08:50:00","17:00:00","17:25:00"));
        Map<String, Object> t2 = new HashMap<>();
        t2.put("station","이공관");
        t2.put("stopTime",List.of("08:25:00","08:35:00","08:40:00","08:45:00","08:50:00","08:55:00","09:00:00","09:05:00","17:15:00","17:40:00"));

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("달구지 조회 성공")
                .list(List.of(t1,t2))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
