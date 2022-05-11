package com.allknu.backend.exception.errors;

import com.allknu.backend.exception.ErrorCode;

public class NotFoundStationTimetableException extends RuntimeException{
    public NotFoundStationTimetableException(){
        super(ErrorCode.NOT_FOUND_STATION_TIMETABLE.getMessage());
    }

    public NotFoundStationTimetableException(Exception ex){
        super(ex);
    }
}
