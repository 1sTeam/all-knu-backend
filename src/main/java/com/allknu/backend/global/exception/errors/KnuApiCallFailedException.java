package com.allknu.backend.global.exception.errors;

import com.allknu.backend.global.exception.ErrorCode;

public class KnuApiCallFailedException extends RuntimeException {

    public KnuApiCallFailedException(){
        super(ErrorCode.KNU_API_FAILED.getMessage());
    }

    public KnuApiCallFailedException(Exception ex){
        super(ex);
    }
}
