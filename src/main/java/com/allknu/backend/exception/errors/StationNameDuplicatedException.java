package com.allknu.backend.exception.errors;


import com.allknu.backend.exception.ErrorCode;

public class StationNameDuplicatedException extends RuntimeException {

    public StationNameDuplicatedException(){
        super(ErrorCode.STATION_NAME_DUPLICATED.getMessage());
    }

    public StationNameDuplicatedException(Exception ex){
        super(ex);
    }
}