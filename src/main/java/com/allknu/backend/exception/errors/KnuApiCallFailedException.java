package com.allknu.backend.exception.errors;

import com.allknu.backend.exception.ErrorCode;

public class KnuApiCallFailedException extends RuntimeException {

    public KnuApiCallFailedException(){
        super(ErrorCode.KNU_API_FAILED.getMessage());
    }

    public KnuApiCallFailedException(Exception ex){
        super(ex);
    }
}
