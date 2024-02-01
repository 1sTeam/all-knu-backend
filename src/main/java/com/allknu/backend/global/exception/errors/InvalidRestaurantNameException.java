package com.allknu.backend.global.exception.errors;

import com.allknu.backend.global.exception.ErrorCode;

public class InvalidRestaurantNameException extends RuntimeException{
    public InvalidRestaurantNameException() {
        super(ErrorCode.INVALID_RESTAURANT_NAME.getMessage());
    }
    public InvalidRestaurantNameException(Exception ex) {
        super(ex);
    }
}
