package com.allknu.backend.global.exception.errors;

import com.allknu.backend.global.exception.ErrorCode;

public class StationTimeDuplicatedException extends RuntimeException{
    public StationTimeDuplicatedException(){
        super(ErrorCode.STATION_TIME_DUPLICATED.getMessage());
    }

    public StationTimeDuplicatedException(Exception ex){
        super(ex);
    }
}
