package com.allknu.backend.exception.errors;

import com.allknu.backend.exception.ErrorCode;

public class LoginFailedException extends RuntimeException {

    public LoginFailedException(){
        super(ErrorCode.LOGIN_FAILED.getMessage());
    }

    public LoginFailedException(Exception ex){
        super(ex);
    }
}