package com.allknu.backend.global.exception.errors;

import com.allknu.backend.global.exception.ErrorCode;

public class NotFoundStationTimetableException extends RuntimeException{
    public NotFoundStationTimetableException(){
        super(ErrorCode.NOT_FOUND_STATION_TIMETABLE.getMessage());
    }

    public NotFoundStationTimetableException(Exception ex){
        super(ex);
    }
}
