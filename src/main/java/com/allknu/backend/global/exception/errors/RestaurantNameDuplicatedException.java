package com.allknu.backend.global.exception.errors;

import com.allknu.backend.global.exception.ErrorCode;

public class RestaurantNameDuplicatedException extends RuntimeException {

    public RestaurantNameDuplicatedException(){
        super(ErrorCode.RESTAURANT_NAME_DUPLICATED.getMessage());
    }

    public RestaurantNameDuplicatedException(Exception ex){
        super(ex);
    }

}
