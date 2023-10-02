package com.allknu.backend.global.exception.errors;


import com.allknu.backend.global.exception.ErrorCode;

public class StationNameDuplicatedException extends RuntimeException {

    public StationNameDuplicatedException(){
        super(ErrorCode.STATION_NAME_DUPLICATED.getMessage());
    }

    public StationNameDuplicatedException(Exception ex){
        super(ex);
    }
}