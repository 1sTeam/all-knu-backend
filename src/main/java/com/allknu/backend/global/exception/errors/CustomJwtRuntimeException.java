package com.allknu.backend.global.exception.errors;

import com.allknu.backend.global.exception.ErrorCode;

public class CustomJwtRuntimeException extends RuntimeException {

    public CustomJwtRuntimeException(){
        super(ErrorCode.AUTHENTICATION_FAILED.getMessage());
    }

    public CustomJwtRuntimeException(Exception ex){
        super(ex);
    }
}
