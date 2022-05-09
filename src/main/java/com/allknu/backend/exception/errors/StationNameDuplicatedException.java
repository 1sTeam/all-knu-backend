package com.allknu.backend.exception.errors;


import com.allknu.backend.exception.ErrorCode;

public class StationNameDuplicatedException extends RuntimeException {

    public StationNameDuplicatedException(){
        super(ErrorCode.REGISTER_FAILED.getMessage());
    }

    public StationNameDuplicatedException(Exception ex){
        super(ex);
    }
}