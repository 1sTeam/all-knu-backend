package com.allknu.backend.exception.errors;

import com.allknu.backend.exception.ErrorCode;

public class RestaurantNameDuplicatedException extends RuntimeException {

    public RestaurantNameDuplicatedException(){
        super(ErrorCode.RESTAURANT_NAME_DUPLICATED.getMessage());
    }

    public RestaurantNameDuplicatedException(Exception ex){
        super(ex);
    }

}
