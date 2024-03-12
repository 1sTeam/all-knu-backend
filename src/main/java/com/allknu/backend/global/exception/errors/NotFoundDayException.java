package com.allknu.backend.global.exception.errors;

import com.allknu.backend.global.exception.ErrorCode;

public class NotFoundDayException extends RuntimeException{

    public NotFoundDayException(){ super(ErrorCode.NOT_FOUND_DAY.getMessage()); }

    public NotFoundDayException(Exception ex){
        super(ex);
    }

}
