package com.allknu.backend.exception.errors;

import com.allknu.backend.exception.ErrorCode;

public class NotFoundStationException extends RuntimeException{
    public NotFoundStationException(){
        super(ErrorCode.NOT_FOUND_STATION.getMessage());
    }

    public NotFoundStationException(Exception ex){
        super(ex);
    }
}
