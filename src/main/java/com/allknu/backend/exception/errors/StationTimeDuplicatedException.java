package com.allknu.backend.exception.errors;

import com.allknu.backend.exception.ErrorCode;

public class StationTimeDuplicatedException extends RuntimeException{
    public StationTimeDuplicatedException(){
        super(ErrorCode.STATION_TIME_DUPLICATED.getMessage());
    }

    public StationTimeDuplicatedException(Exception ex){
        super(ex);
    }
}
