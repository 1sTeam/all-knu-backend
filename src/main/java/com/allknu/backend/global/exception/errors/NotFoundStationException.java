package com.allknu.backend.global.exception.errors;

import com.allknu.backend.global.exception.ErrorCode;

public class NotFoundStationException extends RuntimeException{
    public NotFoundStationException(){
        super(ErrorCode.NOT_FOUND_STATION.getMessage());
    }

    public NotFoundStationException(Exception ex){
        super(ex);
    }
}
